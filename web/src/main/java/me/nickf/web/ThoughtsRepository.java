package me.nickf.web;

public interface ThoughtsRepository extends CrudRepository<Thoughts, String> {
	public Iterable<Thoughts> findAllByOrderByCreatedDesc();
}
