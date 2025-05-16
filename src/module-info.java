module Reading_Room_Project_Assignment_2 {
	requires javafx.controls;
	requires javafx.fxml;
	requires java.sql;
//	requires org.slf4j;
	requires org.xerial.sqlitejdbc;
	requires javafx.graphics;
	requires javafx.base;
	
	opens application to javafx.graphics, javafx.fxml;
}
