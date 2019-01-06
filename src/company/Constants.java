package company;

import java.util.HashMap;
import model.ModelOption;

public final class Constants {
	private Constants() {
	}
	public static class Environments {
		public static final String STORAGE_KEY = "STORAGE_TYPE";
	}
	
	//TODO: 各種モデルの属性とその型を設定値的に操作できるよう実装する.
	// => モデルの属性追加や削除, 変更等を行いたいとき現状Jsonの読み書き, SQLの読み書き(テーブルの作成), Utilitiesの読み書きと3か所変更しなければならずバグの原因となる
	// => モデルの属性MapにkeyとValueのTypeを用意するとやりやすそう
}
