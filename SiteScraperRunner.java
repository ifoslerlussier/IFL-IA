import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.ArrayList;

public class SiteScraperRunner {
	
	public String url;
	public Document doc;
	public URL swatchFolder;
	
	
	
	public SiteScraperRunner(String urlin) throws IOException{
		url = urlin;
		doc = this.getDocument(urlin);
		
	}
	
	
	public Document getDocument(String url) throws IOException{
		Connection conn = Jsoup.connect(url);
		Document document = null;
		
		try{
			document = conn.get();
			
		}catch(IOException e){
			System.out.println("Error: "+e);
		}
		return document;
	}
	
	public void getFolder() throws IOException{
		swatchFolder = IORunner.class.getResource("image_cache");
		System.out.println("folder: "+swatchFolder);
	}
	
	public void saveSwatch(String attribute, String name) throws IOException{
		try {
		URL link = new URL(attribute);
		Response resultImageResponse = Jsoup.connect(link.toString()).ignoreContentType(true).execute();
		FileOutputStream out = (new FileOutputStream(new java.io.File("image_cache" + ""+name)));
		out.write(resultImageResponse.bodyAsBytes());  // resultImageResponse.body() is where the image's contents are.
		out.close();
		}catch(IOException e) {
			System.out.println("Error: "+e);
		}
	}
	
	public void showResults() throws IOException{
		//try {
			System.out.println("\nResults:"); 
			String results = doc.getElementsByClass("results-hits").text();
			System.out.println(results);
			
		/*}catch(IOException e) {
			System.out.println("Error: "+e);
		}*/
	}
	
	public void getSearchResults() throws IOException{
		Elements prodNames = doc.getElementsByClass("product-name");
		String[] nameOuts = new String[prodNames.size()];
		//Elements priceStores = new Elements();
		//Elements salePriceStores = new Elements();
		
		ArrayList<String> priceStores = new ArrayList<String>();
		ArrayList<String> salePriceStores = new ArrayList<String>();
		
		Elements swatches = new Elements();
		
		
	    for(int i=0; i<nameOuts.length; i++) {
		nameOuts[i] = prodNames.get(i).text();
		nameOuts[i] = nameOuts[i].substring(nameOuts[i].indexOf("Go to Product: ")+15);
		Element par = prodNames.get(i).parent();
		Elements parSiblings = par.siblingElements();
		//System.out.println("Parent of Element "+i+" is "+par.className());
		for(int k=0; k<parSiblings.size(); k++) {
		//	System.out.println("Sibling "+k+" of Parent of Element "+i+" is "+parSiblings.get(k).className());
			if(parSiblings.get(k).className().equals("product-tile-bottom-container")) {
		//		System.out.println("\tPrinting Children of "+parSiblings.get(k).className()+":");
				Elements parSiblingsChildren = parSiblings.get(k).children();
				for(int j=0; j<parSiblingsChildren.size(); j++) {
					
					
					if(parSiblingsChildren.get(j).className().equals("product-pricing")) {
		//				System.out.println("\tChild "+j+" of Sibling "+k+" of Parent of Element "+i+" is "+parSiblingsChildren.get(j).className());
						//priceStores.add(parSiblingsChildren.get(j));
						for(int o=0; o<parSiblingsChildren.get(j).children().size(); o++) {
		//					System.out.println("\t\tPricing (child "+o+") of Element "+i+" is "+parSiblingsChildren.get(j).child(o).className());
							if(o==0) {
									priceStores.add(parSiblingsChildren.get(j).child(o).text());
							}else if(o==1) {
								if(!parSiblingsChildren.get(j).child(o).className().equals("null")){
									salePriceStores.add(parSiblingsChildren.get(j).child(o).text());
								}else {
									salePriceStores.add("");
								}
							}
							
							
						}
					}else if(parSiblingsChildren.get(j).className().equals("product-swatches")) {
						System.out.println("\tChild "+j+" of Sibling "+k+" of Parent of Element "+i+" is "+parSiblingsChildren.get(j).className());
						swatches.add(parSiblingsChildren.get(j));
						
						//System.out.println("\tChildren of Child "+j+" of Sibling "+k+" of Parent of Element "+i+" is "+parSiblingsChildren.get(j).children());
						Elements prodSwatchesChildren = parSiblingsChildren.get(j).children();
						for(int f=0; f<parSiblingsChildren.get(j).childrenSize(); f++) {
							System.out.println("\t\tChild "+f+" of Child "+j+" of Sibling "+k+" of Parent of Element "+i+" is "+parSiblingsChildren.get(j).child(f).className());
							if(parSiblingsChildren.get(j).child(f).className().equals("swatch-list")) {
								for(int g=0; g<parSiblingsChildren.get(j).child(f).childrenSize(); g++) {
									System.out.println("\t\t\tChild "+g+" of Swatches-List of Sibling "+k+" of Parent of Element "+i+" is "+parSiblingsChildren.get(j).child(f).child(g).className());
									Element swatchElemInWrapper = parSiblingsChildren.get(j).child(f).child(g).child(0);
									for(int h=0; h<swatchElemInWrapper.childrenSize(); h++) {
										System.out.println("\t\t\tChild "+h+" of Swatch Wrapper"+g+" of Sibling "+k+" of Parent of Element "+i+" is "+swatchElemInWrapper.child(h).className());
										if(swatchElemInWrapper.child(h).className().equals("b-lazy swatch-image")) {
											System.out.println("\t\t\tImagedata Attribute of Child "+h+" of Swatch Wrapper"+g+" of Sibling "+k+" of Parent of Element "+i+" is "+swatchElemInWrapper.child(h).attr("data-src").toString()+"");
											//saveSwatch(swatchElemInWrapper.child(h).attr("data-src").toString(), swatchElemInWrapper.child(h).attr("alt").toString());
											saveSwatch(swatchElemInWrapper.child(h).attr("data-src").toString(), nameOuts[i]+"_"+swatchElemInWrapper.child(h).attr("data-color").toString());
										}
										
										//System.out.println("\t\t\tChild "+h+" of Swatch Wrapper"+g+" of Sibling "+k+" of Parent of Element "+i+" is "+swatchElemInWrapper.child(h).className());
									}
								}
							}
						}
						
						
						
					}
				}
				
			}
		}
		//System.out.println("Siblings of Parent of Element "+i+" is "+parSiblings.toString());
		
		Elements siblings = prodNames.get(i).siblingElements();
		/*int priceIDX = -1;
		for(int elemIDX=0; elemIDX<siblings.size(); elemIDX++) {
			if(siblings.get(elemIDX).className().equals("product-pricing")) {
				priceIDX = elemIDX;
			}
			//System.out.println("Sibling "+elemIDX+" of Element "+i+" is "+siblings.get(elemIDX).className());
		}
		
		if(priceIDX >= 0) {
			//priceStores.add(siblings.get(priceIDX));
			
		}else {
			System.out.println("ruh roh, no pricing found...");
		}
		*/
		//System.out.println(""+prodNames.get(i));
			  /*System.out.println("Name of product number "+i+": ");
			  System.out.println(nameOuts[i]);*/
		}
		Elements prices = doc.getElementsByClass("product-pricing");
		String[] priceOuts = new String[prices.size()];
		  for(int j=0; j<priceOuts.length; j++) {
			  priceOuts[j] = prices.get(j).text();
			  if(priceOuts[j].contains("null")) {
				  priceOuts[j] = "Standard "+priceOuts[j].substring(4,priceOuts[j].indexOf(".")+3);

			  }else {
				  priceOuts[j] = priceOuts[j].substring(0,priceOuts[j].indexOf(".")+3)+","+priceOuts[j].substring(priceOuts[j].indexOf(".")+3);
			  }
			  //priceOuts[j] = priceOuts[j].substring(0,priceOuts[j].indexOf("$")+5)+","+priceOuts[j].substring(priceOuts[j].indexOf("$")+5);
			  
		  }
		  //Elements swatches = doc.getElementsByClass("product-swatches");
		  
		  for(int l=0; l<swatches.size();l++) {
		//	  System.out.println("swatch "+l+" is "+swatches.get(l).toString());
		  }
		  System.out.println(swatches.size());
		  
		 // System.out.println("Prices 2: electric boogaloo");
		  //System.out.println(priceStores.size());
		 /* for(int l=0; l<priceStores.size();l++) {
			  		  String pref = priceStores.get(l).substring(0,priceStores.get(l).indexOf("$"));
			  		  String cost = priceStores.get(l).substring(priceStores.get(l).indexOf("$"));
					  System.out.println("Standard Price "+"of item "+l+" is "+cost);
					  if(!salePriceStores.get(l).equals("")) {
						  pref = salePriceStores.get(l).substring(0,salePriceStores.get(l).indexOf("$"));
				  		  cost = salePriceStores.get(l).substring(salePriceStores.get(l).indexOf("$"));
						  System.out.println(pref+" of item "+l+" is "+cost);
						 // System.out.println(pref+" of item "+l+" is "+salePriceStores.get(l));
						  
					  }
					  System.out.println();
					  
		  }*/
		  System.out.println("priceStores: "+priceStores.size()+" priceOuts: "+priceOuts.length+" nameOuts: "+nameOuts.length+"");
		  
		  String pref;
		  String cost;
		  
		  for(int k=0; k<nameOuts.length; k++) {
			  System.out.print("Name of product number "+k+": ");
			  System.out.println(nameOuts[k]);
			  //System.out.print("Price of product number "+k+": ");
			  /*System.out.println("Price of product number "+k+" is "+priceStores.get(k));
			  if(!salePriceStores.get(k).equals("")) {
				  System.out.println("sale price "+k+" is "+salePriceStores.get(k));
			  }*/
			  
			  pref = priceStores.get(k).substring(0,priceStores.get(k).indexOf("$"));
	  		  cost = priceStores.get(k).substring(priceStores.get(k).indexOf("$"));
			  System.out.println("Standard Price "+"of item "+k+" is "+cost);
			  if(!salePriceStores.get(k).equals("")) {
				  pref = salePriceStores.get(k).substring(0,salePriceStores.get(k).indexOf("$"));
		  		  cost = salePriceStores.get(k).substring(salePriceStores.get(k).indexOf("$"));
				  System.out.println(pref+"of item "+k+" is "+cost);
				 // System.out.println(pref+" of item "+l+" is "+salePriceStores.get(l));
				  
			  }
			  
			  
			  System.out.println();
		  }
	}

}
