import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

public class Mat2Imagetest extends JFrame{
	
	private JFrame frame;
	private JLabel lblNewLabel;
	private JPanel contentPane;
	private Mat src;
	private JMenuBar menuBar;
	private JMenu menu;
	private JMenuItem menuItem;
	private String filename = ("");
	private final Action action_1 = new SwingAction_1();
	
	static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

	public static BufferedImage Mat2Image(Mat src) {

		//Mat srcのチャネル数を取得
			    int type = 0;
			    if (src.channels() == 1) {
			        type = BufferedImage.TYPE_BYTE_GRAY;
			    } else if (src.channels() == 3) {
			        type = BufferedImage.TYPE_3BYTE_BGR;
			    } else {
			        return null;
			    }
		//新規BufferedImage型をsrcの幅，縦，チャネル数で作成．
			    BufferedImage image = new BufferedImage(src.width(), src.height(), type);
		//作成したBufferedImageからRasterを抜き出す.
			    WritableRaster raster = image.getRaster();
		//抜き出したRasterからバッファを抜き出す．
			    DataBufferByte Buf = (DataBufferByte) raster.getDataBuffer();
			    byte[] data = Buf.getData();
			    src.get(0, 0, data);

			    return image;
			}

	public static void main(String args[]){
					Mat2Imagetest frame = new Mat2Imagetest();
					frame.setResizable(false);
					frame.setVisible(true);
				} 
		
	  

	public Mat2Imagetest(){
		setTitle("Mad2Image");
		setBounds(100, 100, 500, 545);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	    EtchedBorder border = new EtchedBorder(EtchedBorder.RAISED, Color.white, Color.black);

	    contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

	    menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		menu = new JMenu("ファイル");
		menuBar.add(menu);

		menuItem = new JMenuItem("開く");
		menuItem.setAction(action_1);
		menu.add(menuItem);
	    
	    lblNewLabel= new JLabel();
		lblNewLabel.setBounds(5, 5, 480, 480);
		lblNewLabel.setPreferredSize(new Dimension(200,50));
	    lblNewLabel.setBorder(border);
		contentPane.add(lblNewLabel);


	}

	private class SwingAction_1 extends AbstractAction {
		public SwingAction_1() {
			putValue(NAME, "開く");
			putValue(SHORT_DESCRIPTION, "入力画像を開きます");
		}

		public void actionPerformed(ActionEvent e) {
			JFileChooser filechooser = new JFileChooser(); // ファイル選択用クラス
			FileNameExtensionFilter filter = new FileNameExtensionFilter("画像ファイル(*.png,*.jpg)", "png", "jpg");
			filechooser.addChoosableFileFilter(filter);
			filechooser.setAcceptAllFileFilterUsed(false);
			filechooser.setDialogTitle("読み込む画像");
			int selected = filechooser.showOpenDialog(frame); //「開く」ダイアログ表示
			if (selected == JFileChooser.APPROVE_OPTION){ //ファイルが選択されたら
			File file = filechooser.getSelectedFile();
			filename = (file.getPath());
				src = Imgcodecs.imread(filename,1);
				ImageIcon image = new ImageIcon(Mat2Image(src));
				lblNewLabel.setIcon(image);
				
		}
	}
}
	
}
