package com.luretechnologies.tms.ui.view.admin.roles;

public class Todo {

	String task;
	boolean done;
	
	
	public Todo() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Todo(String task, boolean done) {
		super();
		this.task = task;
		this.done = done;
	}


	
	/**
	 * @return the task
	 */
	public String getTask() {
		return task;
	}


	/**
	 * @param task the task to set
	 */
	public void setTask(String task) {
		this.task = task;
	}


	/**
	 * @return the done
	 */
	public boolean isDone() {
		return done;
	}


	/**
	 * @param done the done to set
	 */
	public void setDone(boolean done) {
		this.done = done;
	}
	
	
	
}
