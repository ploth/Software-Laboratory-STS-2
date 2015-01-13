package test;

import java.nio.file.FileSystems;
import java.nio.file.Path;

public class PathTest {

	public static void main(String[] args) {
		String defaultDatabaseName_ = "database.dbs";
		Path path = FileSystems.getDefault().getPath(defaultDatabaseName_);
		System.out.println(path.toString());
	}

}
