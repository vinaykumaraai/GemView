package com.discovery.view.dialog;

import com.vaadin.ui.Component;
import com.vaadin.ui.Window;

public abstract class DialogWindow extends Window {

	public DialogWindow() {
//		this.setModal(true);
		this.setClosable(true);
		this.setResizable(true);
		this.setDraggable(true);
		this.setHeight("30%");
		this.setWidth("50%");
		this.setPosition(50,50);
		/*this.addContextClickListener(click->{
			if(click.getButton().equals(MouseButton.RIGHT)){
				this.setPosition(this.getPositionX(), 0);
				this.setHeight("1%");
				
			}
		});*/
	}
	
	public abstract Component getContent();
}
