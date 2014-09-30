package com.example.hateburssreader;

public class ListItem {
	private String title;
	private String link;
	
	public ListItem(String _title, String _link) {
		this.title = _title;
		this.link = _link;
	}

	public String getLink(){
		return this.link;
	}
	
	@Override
	public String toString(){
		return this.title;
	}
}
