package bigWork;

import java.io.File;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;


class TextFileUtils{
    public static String read( File f, String charset) throws Exception
    {
        FileInputStream fstream = new FileInputStream(f);
        try{
            int fileSize = (int)f.length();
            if(fileSize > 1024*512)
                throw new Exception("File too large to read! size=" + fileSize);

            byte[] buffer = new byte[ fileSize ];
            fstream.read(buffer);
            return new String(buffer, charset);
        }finally
        {
            try{ fstream.close();}catch (Exception e){}
        }
    }

    public static void write(File f, String text, String charset) throws Exception
    {
        FileOutputStream fstream = new FileOutputStream(f);
        try{
            fstream.write( text.getBytes( charset ));
        }finally
        {
            fstream.close();
        }
    }
}

public class textEditor extends Application {	
	
  public static void main(String[] args) {
    launch();
  }
	
  @Override
  public void start(Stage primaryStage) {
	  	  
	try { 
    BorderPane root = new BorderPane();
    Scene scene = new Scene(root, 600, 400, Color.WHITE);
	
    MenuBar menuBar = new MenuBar();// 创建菜单条
    TextArea TextArea = new TextArea();// 创建文本域编辑框
   
    root.setTop(menuBar);// 菜单条置顶
    root.setCenter(TextArea);// 文本域编辑框居中
       
    Menu fileMenu = new Menu("文件(F)");// 创建文件下拉菜单
    Menu editMenu = new Menu("编辑(E)");// 创建编辑下拉菜单
    Menu helpMenu = new Menu("帮助(H)");// 创建关于下拉菜单
        
    // 创建菜单里的元素
    MenuItem newMenuItem    = new MenuItem("新建(N)");   
    MenuItem openMenuItem   = new MenuItem("打开(O)");
    MenuItem saveMenuItem   = new MenuItem("保存(S)");   
    MenuItem exitMenuItem   = new MenuItem("退出(E)");    
	MenuItem copyMenuItem   = new MenuItem("复制(C)");
	MenuItem pasteMenuItem  = new MenuItem("粘贴(P)");
	MenuItem cutMenuItem    = new MenuItem("剪切(T)");
	MenuItem totselMenuItem = new MenuItem("全选(A)");
	MenuItem deleteMenuItem = new MenuItem("删除(D)");
	MenuItem weightMenuItem = new MenuItem("加粗(B)");
	MenuItem postMenuItem   = new MenuItem("斜体(I)");
	MenuItem aboutMenuItem  = new MenuItem("关于(A)");
	MenuItem moreMenuItem   = new MenuItem("更多(M)");
	

    // 把菜单里的元素添加到下拉菜单里
    fileMenu.getItems().addAll(newMenuItem,openMenuItem, saveMenuItem,
    		new SeparatorMenuItem(), exitMenuItem);
    editMenu.getItems().addAll(copyMenuItem, pasteMenuItem,cutMenuItem,totselMenuItem,
    		deleteMenuItem,postMenuItem,weightMenuItem);
    helpMenu.getItems().addAll(aboutMenuItem,moreMenuItem);  
    // 把下拉菜单里的元素添加到菜单条里
    menuBar.getMenus().addAll(fileMenu,editMenu,helpMenu);

   
	// 为菜单元素捕获行为事件	
    // 新建
    newMenuItem.setOnAction(new EventHandler<ActionEvent>() {
 	@Override
 	public void handle(ActionEvent event)
 	{
 		TextArea.clear();
 	}				
 	});
    
    //打开
    openMenuItem.setOnAction(new EventHandler<ActionEvent>() {
 	@Override
 	public void handle(ActionEvent event)
 	{
 		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("打开文件");
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Text Files", "*.txt"));

		File selectedFile = fileChooser.showOpenDialog(primaryStage);
		if(selectedFile == null) return; // 用户没有选中文件, 已经取消操作
		
		try {
			String text = TextFileUtils.read(selectedFile, "GBK");
			TextArea.setText(text);
		}catch(Exception e)
		{
			e.printStackTrace();
		}	
 	}				
 	});
    

    //保存
    saveMenuItem.setOnAction(new EventHandler<ActionEvent>() {
 	@Override
 	public void handle(ActionEvent event)
 	{
 		
 		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("保存文件");
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Text Files", "*.txt"));

		File selectedFile = fileChooser.showSaveDialog(primaryStage);
		if(selectedFile == null) return; //用户没有选中文件, 已经取消操作
		
		try {
			String text = TextArea.getText();
			TextFileUtils.write(selectedFile, text, "GBK");
			Alert warning = new Alert(AlertType.INFORMATION);
			warning.setHeaderText("保存成功！");
			warning.showAndWait();			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
 	
 	}				
 	});
      
    //复制
    copyMenuItem.setOnAction(new EventHandler<ActionEvent>() {
 	@Override
 	public void handle(ActionEvent event)
 	{
 		TextArea.copy();
 	}				
 	});
     
    //粘贴
    pasteMenuItem.setOnAction(new EventHandler<ActionEvent>() {
 	@Override
 	public void handle(ActionEvent event)
 	{
 		TextArea.paste();
 	}					
 	});
    
    //剪切
    cutMenuItem.setOnAction(new EventHandler<ActionEvent>() {
 	@Override
 	public void handle(ActionEvent event)
 	{		
 		TextArea.cut();		
 	}				
 	});
       
    //全选
    totselMenuItem.setOnAction(new EventHandler<ActionEvent>() {
 	@Override
 	public void handle(ActionEvent event)
 	{  		
 		TextArea.selectAll();
 	}				
 	});
  
    
    //删除
    deleteMenuItem.setOnAction(new EventHandler<ActionEvent>() {
 	@Override
 	public void handle(ActionEvent event)
 	{  		
 	    int start = TextArea.getCaretPosition(); //得到要删除的字符串的起始位置
 	    int len   = TextArea.getSelectedText().length(); //得到要删除的字符串的长度
 	    TextArea.deleteText( start , start+len); ///删除所选中的字符串
 	  
 	}				
 	});
    
    
    //加粗
    weightMenuItem.setOnAction(new EventHandler<ActionEvent>() {
    // 	@Override
     	public int i; 
     	public void handle(ActionEvent event)
     	{
     		Font font=TextArea.getFont();
     		if (i%2==0) {
     			i++;
     			TextArea.setFont(Font.font(font.getName(),FontWeight.BOLD,font.getSize()));     		
     		}
     		else {
     			i++;
     			TextArea.setFont(Font.font(font.getName(),FontWeight.LIGHT,font.getSize()));     		
     		  }    		    
     		}				
     	});

    
    //斜体
    postMenuItem.setOnAction(new EventHandler<ActionEvent>() {
     	public int i; 
     	public void handle(ActionEvent event)
     	{
     		Font font=TextArea.getFont();
     		if (i%2==0) {
     			i++;
     			TextArea.setFont(Font.font(font.getName(),FontPosture.ITALIC,font.getSize()));     		
     		}
     		else {
     			i++;
     			TextArea.setFont(Font.font(font.getName(),FontPosture.REGULAR,font.getSize()));     		
     		  }    		    
     		}					
     	});
    
    
    // 关于
    aboutMenuItem.setOnAction(new EventHandler<ActionEvent>() {
 	@Override
 	public void handle(ActionEvent event)
 	{
 		Alert warning = new Alert(AlertType.INFORMATION);
 		warning.setTitle("关于"); //添加标题
		warning.setHeaderText("文本编译器程序");
		warning.setContentText("JavaProject ：TextEditor");
		warning.showAndWait();			
 	}				
 	});
    
    // 更多
    moreMenuItem.setOnAction(new EventHandler<ActionEvent>() {
 	@Override
 	public void handle(ActionEvent event)
 	{
 		Alert warning = new Alert(AlertType.INFORMATION);
 		warning.setTitle("更多"); //添加标题
		warning.setHeaderText("文本编译器");
		warning.setContentText("用户可以创建、打开和编辑简单的基于文本的文件。"
			+"提供基本文本编译功能，包括保存、复制、粘贴和打印能力。更多功能，请敬请期待！");
		warning.showAndWait();			
 	}				
 	});
	
    //退出 
    exitMenuItem.setOnAction(new EventHandler<ActionEvent>() {
    @Override
    public void handle(ActionEvent event)
    {  
    	Alert warning = new Alert(Alert.AlertType.CONFIRMATION);
    	warning.setTitle("提示"); //添加标题
		warning.setHeaderText("文本编译器");
		warning.setContentText("是否确定退出？");
		warning.showAndWait();			
     	System.exit(0);     	  
    }				
    });
       
    primaryStage.setTitle("TextEditor");
    primaryStage.setScene(scene);
    primaryStage.show();
	} catch(Exception e) {		
		e.printStackTrace();
	}
  }

}

