package mars.mp3player.service;

import mars.download.HttpDownloader;
import mars.model.Mp3Info;
import mars.mp3player.AppConstant;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class DownloadService extends Service{

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	//ÿ���û����ListActivity���е�һ����Ŀʱ���ͻ���ø÷���
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		//��Intent�����н�Mp3Info����ȡ��
		Mp3Info mp3Info = (Mp3Info)intent.getSerializableExtra("mp3Info");
		//����һ�������̣߳�����Mp3Info������Ϊ�������ݵ��̶߳�����
		DownloadThread downloadThread = new DownloadThread(mp3Info);
		//�������߳�
		Thread thread = new Thread(downloadThread);
		thread.start();
		return super.onStartCommand(intent, flags, startId);
	}
	
	class DownloadThread implements Runnable{
		private Mp3Info mp3Info = null;
		public DownloadThread(Mp3Info mp3Info){
			this.mp3Info = mp3Info;
		}
		@Override
		public void run() {
			//���ص�ַhttp://192.168.1.107:8081/mp3/a1.mp3
			//����MP3�ļ������֣��������ص�ַ
			String mp3Url = AppConstant.URL.BASE_URL + mp3Info.getMp3Name();
			String lrcUrl = AppConstant.URL.BASE_URL + mp3Info.getLrcName();
			//���������ļ����õĶ���
			HttpDownloader httpDownloader = new HttpDownloader();
			//���ļ��������������洢��SDCard����
			int mp3Result = httpDownloader.downFile(mp3Url, "mp3/", mp3Info.getMp3Name());
			int lrcResult = httpDownloader.downFile(lrcUrl, "mp3/", mp3Info.getLrcName());
			/*String resultMessage = null;
			if(result == -1){
				resultMessage = "����ʧ��";
			}
			else if(result == 0){
				resultMessage = "�ļ��Ѿ����ڣ�����Ҫ�ظ�����";
			}
			else if(result == 1){
				resultMessage = "�ļ����سɹ�";
			}*/
			//ʹ��Notification��ʾ�ͻ����ؽ��
		}
		
	}

}
