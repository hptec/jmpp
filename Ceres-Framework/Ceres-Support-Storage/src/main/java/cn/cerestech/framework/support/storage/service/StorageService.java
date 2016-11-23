package cn.cerestech.framework.support.storage.service;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.base.Strings;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Lists;
import com.google.common.io.Files;

import cn.cerestech.framework.core.http.Https;
import cn.cerestech.framework.core.service.Result;
import cn.cerestech.framework.core.utils.Random;
import cn.cerestech.framework.support.storage.QueryRequest;
import cn.cerestech.framework.support.storage.dao.StorageDao;
import cn.cerestech.framework.support.storage.entity.StorageFile;
import cn.cerestech.framework.support.storage.enums.ErrorCodes;

@Service
@ConfigurationProperties(prefix = "sys")
public class StorageService {

	@NotNull
	private String filestorage;

	private Logger log = LogManager.getLogger();

	public static LoadingCache<String, Optional<StorageFile>> cachedKV = null;

	@PostConstruct
	public void init() {
		if (cachedKV == null) {
			cachedKV = CacheBuilder.newBuilder().expireAfterWrite(3, TimeUnit.SECONDS)
					.build(new CacheLoader<String, Optional<StorageFile>>() {
						@Override
						public Optional<StorageFile> load(String httpUri) throws Exception {
							return Optional.ofNullable(queryByHttpUri(httpUri));
						}
					});
		}
	}

	@Autowired
	protected StorageDao storageDao;

	@Autowired
	protected FilterService filterService;

	/**
	 * 返回工程文件存储目录，不带结束“/”
	 * 
	 * @return
	 */
	public String path() {
		return filestorage;
	}

	protected String path(String path) {
		String dirPath = path() + (path.startsWith(File.separator) ? path : File.separator + path);
		File f = new File(dirPath);
		if (!f.exists()) {
			boolean success = f.mkdirs();
			if (!success) {
				log.error("[ERROR]创建目录文件目录失败！！！请管理员检查是否授权目录！！！");
			}
		}
		return dirPath;
	}

	public String path(String... path) {
		StringBuffer buffer = new StringBuffer(path());
		if (path != null && path.length != 0) {
			for (String p : path) {
				buffer.append((p.startsWith(File.separator) ? p : File.separator + p));
			}
		}
		File f = new File(buffer.toString());
		if (!f.exists()) {
			boolean success = f.mkdirs();
			if (!success) {
				log.error("[ERROR]创建目录文件目录失败！！！请管理员检查是否授权目录！！！");
			}
		}
		return buffer.toString();
	}

	public Optional<StorageFile> queryCache(String httpUri) {
		try {
			return cachedKV.get(httpUri);
		} catch (ExecutionException e) {
			return Optional.empty();
		}
	}

	protected StorageFile queryByHttpUri(String httpUri) {

		// 首先检测相同过滤器的文件是否已经存在
		StorageFile file = storageDao.findUniqueByHttpUri(httpUri);
		if (file == null) {
			// 数据库记录不存在
			QueryRequest request = QueryRequest.fromHttpUri(httpUri);// 格式化请求
			// 检测本地原件
			file = queryByLocalUri(request.getLocal_uri());
			if (file == null) {
				// 本地文件也不存在，返回空
				return null;
			} else {
				// 原件存在，补全文件记录。
				if (!Strings.isNullOrEmpty(request.getFilterString())) {
					// 需要进行特效处理
					file = filterService.filter(file, request.getFilterString());
				}

				// 生成文件名
				StorageFile newFile = StorageFile.fromLocalUri(file.getLocalUri(), request.getFilterString(),
						file.getBytes(), file.getUploadName());
				writeByLocalUri(newFile.getLocalUri(), newFile.getBytes());
				storageDao.save(newFile);
				return newFile;
			}

		} else {
			// 记录存在，直接返回
			file = queryByLocalUri(file.getLocalUri());
			return file;
		}

	}

	/**
	 * 删除LocalFile与其对应的文件
	 * 
	 * @param id
	 */
	protected void delete(Long id) {
		if (id == null) {
			return;
		}
		StorageFile f = storageDao.findOne(id);
		if (f == null) {
			return;
		}

		// 删除文件
		removeFile(f.getLocalUri());

		storageDao.delete(id);
	}

	public StorageFile put(String orignalName, byte[] bytes) {

		if (bytes == null || bytes.length == 0) {
			return null;
		}

		Calendar cale = Calendar.getInstance();
		int year = cale.get(Calendar.YEAR);
		int month = cale.get(Calendar.MONTH) + 1;
		int day = cale.get(Calendar.DATE);

		String idPath = File.separator + year + File.separator + month + File.separator + day + File.separator;

		StorageFile localFile = new StorageFile();

		localFile.setBytes(bytes);
		localFile.setExtensionName(Files.getFileExtension(orignalName));
		localFile.setSimpleName(Files.getNameWithoutExtension(orignalName) + "." + Files.getFileExtension(orignalName));
		localFile.setLocalUri(idPath + Random.uuid() + "." + Files.getFileExtension(orignalName));
		localFile.setHttpUri(localFile.getLocalUri());
		localFile.setSize(bytes.length + 0L);
		localFile.setUploadName(orignalName);

		// 保存文件内容
		writeByLocalUri(localFile.getLocalUri(), bytes);
		// 保存文件记录
		storageDao.save(localFile);

		return localFile;
	}

	public StorageFile put(String uri, String orignalName, byte[] bytes) {

		if (bytes == null || bytes.length == 0) {
			return null;
		}

		if (uri.startsWith("/")) {
			uri = uri.substring(1, uri.length());
		}

		StorageFile localFile = new StorageFile();

		localFile.setBytes(bytes);
		localFile.setExtensionName(Files.getFileExtension(uri));
		localFile.setSimpleName(Files.getNameWithoutExtension(uri) + "." + Files.getFileExtension(uri));
		localFile.setLocalUri(uri);
		localFile.setHttpUri(localFile.getLocalUri());
		localFile.setSize(bytes.length + 0L);
		localFile.setUploadName(orignalName);

		// 保存文件内容
		writeByLocalUri(localFile.getLocalUri(), bytes);
		// 保存文件记录
		storageDao.save(localFile);

		return localFile;
	}

	/**
	 * 存储全路径
	 * 
	 * @param relativePath
	 * @param bytes
	 * @return
	 */
	protected String writeByLocalUri(String localUri, byte[] bytes) {
		String absolutePath = path(getPathOnly(localUri)) + File.separatorChar + getFileName(localUri);
		try {
			Files.write(bytes, new File(absolutePath));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return absolutePath;
	}

	/**
	 * 查询FileId记录，如果记录不存在，源存在，则还需补足记录
	 * 
	 * @param fileId
	 * @return
	 */
	protected StorageFile queryByLocalUri(String localUri) {

		StorageFile file = storageDao.findUniqueByLocalUri(localUri);

		byte[] bytes = new byte[0];
		if (file == null) {
			// 记录不存在
			// 检测是否在文件系统上是否存在
			if (fileExist(localUri)) {
				// 记录不存在但是文件存在
				bytes = readFileSystem(localUri);
				if (bytes != null && bytes.length > 0) {
					// 字节数组有内容，表示文件存在
					String ext = Files.getFileExtension(localUri);
					if (Strings.isNullOrEmpty(ext)) {
						// 必须有扩展名用于识别格式。
						return null;
					}
					// 补完记录
					file = new StorageFile();
					file.setHttpUri(localUri);
					file.setLocalUri(localUri);
					file.setSimpleName(Files.getNameWithoutExtension(localUri) + "." + ext);
					file.setExtensionName(ext);
					file.setSize(bytes.length + 0L);
					file.setUploadName(null);
					storageDao.save(file);
					file.setBytes(bytes);
					return file;
				}
			}
		} else {
			// 记录存在
			if (!fileExist(localUri)) {
				// 记录存在文件不存在
				// 源不存在，记录存在，则删除记录
				storageDao.delete(file.getId());
			} else {
				// 记录存在，文件存在
				bytes = readFileSystem(localUri);
				if (bytes != null && bytes.length > 0) {
					file.setBytes(bytes);
					return file;
				}
			}

		}

		return null;
	}

	protected byte[] readFileSystem(String fileId) {
		String absolutePath = path(getPathOnly(fileId)) + File.separatorChar + getFileName(fileId);
		File absoluteFile = new File(absolutePath);
		if (!absoluteFile.isDirectory()) {
			if (absoluteFile.exists()) {
				try {
					return Files.toByteArray(absoluteFile);
				} catch (IOException e) {
					log.catching(e);
				}
			}
		}
		return new byte[0];
	}

	protected Boolean fileExist(String id) {
		String file = path() + File.separatorChar + id;
		return new File(file).exists();
	}

	protected void removeFile(String fileId) {
		String absolutePath = path(getPathOnly(fileId)) + File.separatorChar + getFileName(fileId);
		File f = new File(absolutePath);
		if (f.exists()) {
			f.delete();
		}
	}

	/**
	 * 在传入的文件中，解析出文件的相对目录结构。
	 * 
	 * @param fileId
	 * @return
	 */
	private String getPathOnly(String fileId) {
		String name = getFileName(fileId);
		StringBuffer buffer = new StringBuffer(fileId);
		buffer.delete(buffer.length() - name.length(), buffer.length());
		return buffer.toString();
	}

	private String getFileName(String fileId) {
		String ext = Files.getFileExtension(fileId);
		String name = Files.getNameWithoutExtension(fileId);
		if (!Strings.isNullOrEmpty(ext)) {
			return name + "." + ext;
		} else {
			return name;
		}
	}

	public List<StorageFile> put(List<MultipartFile> files) {
		List<StorageFile> retList = Lists.newArrayList();
		files.forEach(file -> {
			StorageFile localFile;
			try {
				localFile = put(file.getOriginalFilename(), file.getBytes());
				if (localFile != null) {
					retList.add(localFile);
				}
			} catch (Exception e) {
				log.catching(e);
			}
		});
		return retList;
	}

	/**
	 * 检测文件存储是否打开
	 * 
	 * @return
	 */
	public Boolean isFileStorageOpen() {

		File f = new File(filestorage);
		if (!f.exists()) {
			log.debug("FileStorage not exist");
			return Boolean.FALSE;
		}

		if (!f.canRead()) {
			log.debug("FileStorage cannot read");
			return Boolean.FALSE;
		}

		if (!f.canWrite()) {
			log.debug("FileStorage cannot write");
			return Boolean.FALSE;
		}

		return Boolean.TRUE;

	}

	/**
	 * 从互联网上下载一个文件，如果本地已经下载则直接返回
	 * 
	 * @param uri
	 * @param putToDir
	 *            指定放入哪个目录
	 * @return
	 */
	public StorageFile download(String uri, String putToDir) {
		StorageFile file = storageDao.findUniqueByUploadName(uri);
		if (file == null) {
			// 没有找到，则下载新增
			byte[] bytes = Https.of().get(uri).readByte();
			String simpleName = Files.getNameWithoutExtension(uri) + "." + Files.getFileExtension(uri);
			if (!putToDir.endsWith(File.separator)) {
				putToDir = putToDir + File.separator;
			}
			file = put(putToDir + simpleName, uri, bytes);
		}
		return file;
	}

	/**
	 * 从互联网上下载一个文件，如果本地已经下载则直接返回
	 * 
	 * @param uri
	 * @return
	 */
	public StorageFile download(String uri) {
		Calendar cale = Calendar.getInstance();
		int year = cale.get(Calendar.YEAR);
		int month = cale.get(Calendar.MONTH) + 1;
		int day = cale.get(Calendar.DATE);

		String idPath = year + File.separator + month + File.separator + day + File.separator;
		return download(uri, idPath);
	}

	public Result<StorageFile> queryById(Long id) {
		if (id == null) {
			return Result.error(ErrorCodes.FILE_NOT_FOUND);
		}
		StorageFile file = storageDao.findOne(id);
		if (file == null) {
			return Result.error(ErrorCodes.FILE_NOT_FOUND);
		}

		return Result.success(file);
	}

	public String getFilestorage() {
		return filestorage;
	}

	public void setFilestorage(String filestorage) {
		this.filestorage = filestorage;
	}

}
