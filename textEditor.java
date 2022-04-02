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
	
    MenuBar menuBar = new MenuBar();// �����˵���
    TextArea TextArea = new TextArea();// �����ı���༭��
   
    root.setTop(menuBar);// �˵����ö�
    root.setCenter(TextArea);// �ı���༭�����
       
    Menu fileMenu = new Menu("�ļ�(F)");// �����ļ������˵�
    Menu editMenu = new Menu("�༭(E)");// �����༭�����˵�
    Menu helpMenu = new Menu("����(H)");// �������������˵�
        
    // �����˵����Ԫ��
    MenuItem newMenuItem    = new MenuItem("�½�(N)");   
    MenuItem openMenuItem   = new MenuItem("��(O)");
    MenuItem saveMenuItem   = new MenuItem("����(S)");   
    MenuItem exitMenuItem   = new MenuItem("�˳�(E)");    
	MenuItem copyMenuItem   = new MenuItem("����(C)");
	MenuItem pasteMenuItem  = new MenuItem("ճ��(P)");
	MenuItem cutMenuItem    = new MenuItem("����(T)");
	MenuItem totselMenuItem = new MenuItem("ȫѡ(A)");
	MenuItem deleteMenuItem = new MenuItem("ɾ��(D)");
	MenuItem weightMenuItem = new MenuItem("�Ӵ�(B)");
	MenuItem postMenuItem   = new MenuItem("б��(I)");
	MenuItem aboutMenuItem  = new MenuItem("����(A)");
	MenuItem moreMenuItem   = new MenuItem("����(M)");
	

    // �Ѳ˵����Ԫ����ӵ������˵���
    fileMenu.getItems().addAll(newMenuItem,openMenuItem, saveMenuItem,
    		new SeparatorMenuItem(), exitMenuItem);
    editMenu.getItems().addAll(copyMenuItem, pasteMenuItem,cutMenuItem,totselMenuItem,
    		deleteMenuItem,postMenuItem,weightMenuItem);
    helpMenu.getItems().addAll(aboutMenuItem,moreMenuItem);  
    // �������˵����Ԫ����ӵ��˵�����
    menuBar.getMenus().addAll(fileMenu,editMenu,helpMenu);

   
	// Ϊ�˵�Ԫ�ز�����Ϊ�¼�	
    // �½�
    newMenuItem.setOnAction(new EventHandler<ActionEvent>() {
 	@Override
 	public void handle(ActionEvent event)
 	{
 		TextArea.clear();
 	}				
 	});
    
    //��
    openMenuItem.setOnAction(new EventHandler<ActionEvent>() {
 	@Override
 	public void handle(ActionEvent event)
 	{
 		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("���ļ�");
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Text Files", "*.txt"));

		File selectedFile = fileChooser.showOpenDialog(primaryStage);
		if(selectedFile == null) return; // �û�û��ѡ���ļ�, �Ѿ�ȡ������
		
		try {
			String text = TextFileUtils.read(selectedFile, "GBK");
			TextArea.setText(text);
		}catch(Exception e)
		{
			e.printStackTrace();
		}	
 	}				
 	});
    

    //����
    saveMenuItem.setOnAction(new EventHandler<ActionEvent>() {
 	@Override
 	public void handle(ActionEvent event)
 	{
 		
 		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("�����ļ�");
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Text Files", "*.txt"));

		File selectedFile = fileChooser.showSaveDialog(primaryStage);
		if(selectedFile == null) return; //�û�û��ѡ���ļ�, �Ѿ�ȡ������
		
		try {
			String text = TextArea.getText();
			TextFileUtils.write(selectedFile, text, "GBK");
			Alert warning = new Alert(AlertType.INFORMATION);
			warning.setHeaderText("����ɹ���");
			warning.showAndWait();			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
 	
 	}				
 	});
      
    //����
    copyMenuItem.setOnAction(new EventHandler<ActionEvent>() {
 	@Override
 	public void handle(ActionEvent event)
 	{
 		TextArea.copy();
 	}				
 	});
     
    //ճ��
    pasteMenuItem.setOnAction(new EventHandler<ActionEvent>() {
 	@Override
 	public void handle(ActionEvent event)
 	{
 		TextArea.paste();
 	}					
 	});
    
    //����
    cutMenuItem.setOnAction(new EventHandler<ActionEvent>() {
 	@Override
 	public void handle(ActionEvent event)
 	{		
 		TextArea.cut();		
 	}				
 	});
       
    //ȫѡ
    totselMenuItem.setOnAction(new EventHandler<ActionEvent>() {
 	@Override
 	public void handle(ActionEvent event)
 	{  		
 		TextArea.selectAll();
 	}				
 	});
  
    
    //ɾ��
    deleteMenuItem.setOnAction(new EventHandler<ActionEvent>() {
 	@Override
 	public void handle(ActionEvent event)
 	{  		
 	    int start = TextArea.getCaretPosition(); //�õ�Ҫɾ�����ַ�������ʼλ��
 	    int len   = TextArea.getSelectedText().length(); //�õ�Ҫɾ�����ַ����ĳ���
 	    TextArea.deleteText( start , start+len); ///ɾ����ѡ�е��ַ���
 	  
 	}				
 	});
    
    
    //�Ӵ�
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

    
    //б��
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
    
    
    // ����
    aboutMenuItem.setOnAction(new EventHandler<ActionEvent>() {
 	@Override
 	public void handle(ActionEvent event)
 	{
 		Alert warning = new Alert(AlertType.INFORMATION);
 		warning.setTitle("����"); //��ӱ���
		warning.setHeaderText("�ı�����������");
		warning.setContentText("JavaProject ��TextEditor");
		warning.showAndWait();			
 	}				
 	});
    
    // ����
    moreMenuItem.setOnAction(new EventHandler<ActionEvent>() {
 	@Override
 	public void handle(ActionEvent event)
 	{
 		Alert warning = new Alert(AlertType.INFORMATION);
 		warning.setTitle("����"); //��ӱ���
		warning.setHeaderText("�ı�������");
		warning.setContentText("�û����Դ������򿪺ͱ༭�򵥵Ļ����ı����ļ���"
			+"�ṩ�����ı����빦�ܣ��������桢���ơ�ճ���ʹ�ӡ���������๦�ܣ��뾴���ڴ���");
		warning.showAndWait();			
 	}				
 	});
	
    //�˳� 
    exitMenuItem.setOnAction(new EventHandler<ActionEvent>() {
    @Override
    public void handle(ActionEvent event)
    {  
    	Alert warning = new Alert(Alert.AlertType.CONFIRMATION);
    	warning.setTitle("��ʾ"); //��ӱ���
		warning.setHeaderText("�ı�������");
		warning.setContentText("�Ƿ�ȷ���˳���");
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

