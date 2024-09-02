package me.nickf.web;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "thoughts")
public class Thoughts {
	@Id
	public String id;

	public String title;
	public boolean over_18;
	public String author;
	public String permalink;
	public Date created;

	private Thoughts() {
	}

	public Thoughts(String title, Date created, boolean over_18, String id, String author, String permalink) {
		this.title = title;
		this.created = created;
		this.over_18 = over_18;
		this.id = id;
		this.author = author;
		this.permalink = permalink;
	}

	public String getAuthor() {
		return author;
	}

	public Date getCreated() {
		return created;
	}

	public String getId() {
		return id;
	}

	public String getPermalink() {
		return permalink;
	}

	public String getTitle() {
		return title;
	}

	public boolean getOver_18() {
		return over_18;
	}

	@Override
	public String toString() {
		return getTitle() + "\n" + getAuthor() + "\n" + getId() + "\n" + getOver_18() + "\n" + getCreated() + "\n"
				+ getPermalink();
	}

}