package br.com.alura.cornerpro.service;

public class Utils {
	public static int getMinutoForTitle(String title) {
		int indexF = title.indexOf("+", 0);
		if (indexF<0) indexF = title.indexOf("'", 0);
		return Integer.valueOf(title.substring(0,indexF));
	}
	public static double getAttrToStyle(String style, String attr) {
		int attrIndex = style.indexOf(attr);
		int attrIndexF = style.indexOf("%", attrIndex);
		
		return Double.valueOf(style.substring(attrIndex+4,attrIndexF).replace(':',' ').trim());
	}
	public static int getPrevisao(String text) {
		int indexS = text.indexOf(":", 0);
		return Integer.valueOf(text.substring(indexS+2,text.length()));
	}
	public static int getMinutoArredondado(String minutoComMais) {
		int indexF = minutoComMais.indexOf("+", 0);
		return indexF>0 ? Integer.valueOf(minutoComMais.substring(0,indexF)):Integer.valueOf(minutoComMais) ;
	}
}
