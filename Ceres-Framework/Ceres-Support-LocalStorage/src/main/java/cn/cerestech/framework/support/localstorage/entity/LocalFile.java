package cn.cerestech.framework.support.localstorage.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.google.common.io.Files;

import cn.cerestech.framework.core.utils.Random;
import cn.cerestech.framework.support.persistence.IdEntity;

@Entity
@Table(name = "$$sys_local_file")
public class LocalFile extends IdEntity {

	@Column(length = 255)
	// title = "原文件名"
	private String uploadName;

	@Column(length = 2000)
	// 互联网识别路径
	private String httpUri;

	@Column(length = 2000)
	// 本地存储路径
	private String localUri;

	@Column(precision = 10)
	// title = "大小"
	private Long size;

	@Column()
	// 包含名字和扩展名但是不包含路径
	private String simpleName;

	@Column()
	// title = "扩展名"
	private String extensionName;

	@Transient
	private byte[] bytes;// 内容

	public static LocalFile fromLocalUri(String localUri, String filterString, byte[] bytes, String uploaded_name) {
		LocalFile newFile = new LocalFile();

		// 生成新的本地文件名
		String name = Files.getNameWithoutExtension(localUri);
		String ext = Files.getFileExtension(localUri);

		StringBuffer localUriBuffer = new StringBuffer(localUri);
		localUriBuffer.delete(localUriBuffer.length() - name.length() - 1 - ext.length(), localUriBuffer.length());
		String thinString = localUriBuffer.toString();
		String newName = Random.uuid();
		newFile.setLocalUri(thinString + newName + "." + ext);
		newFile.setHttpUri(thinString + name + "@" + filterString + "." + ext);
		newFile.setBytes(bytes);
		newFile.setExtensionName(ext);
		newFile.setSimpleName(newName + "." + ext);
		newFile.setSize(bytes == null ? 0L : bytes.length);
		newFile.setUploadName(uploaded_name);
		return newFile;
	}

	public String getHttpUri() {
		return httpUri;
	}

	public void setHttpUri(String httpUri) {
		this.httpUri = httpUri;
	}

	public String getLocalUri() {
		return localUri;
	}

	public void setLocalUri(String localUri) {
		this.localUri = localUri;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public String getUploadName() {
		return uploadName;
	}

	public void setUploadName(String uploadName) {
		this.uploadName = uploadName;
	}

	public String getSimpleName() {
		return simpleName;
	}

	public void setSimpleName(String simpleName) {
		this.simpleName = simpleName;
	}

	public String getExtensionName() {
		return extensionName;
	}

	public void setExtensionName(String extensionName) {
		this.extensionName = extensionName;
	}

	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

}
