import java.util.List;

import com.google.common.collect.Lists;

public interface ITest {

	default List<String> getDeps(){
		return Lists.newArrayList();
	}
}
