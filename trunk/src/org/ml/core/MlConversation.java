package org.ml.core;

import java.util.ArrayList;
import java.util.Calendar;

public class MlConversation {

	ArrayList<MlConversationPart> parts;
	long startTime;
	
	public MlConversation(){
		setStartTime(Calendar.getInstance().getTime().getTime());
		parts = new ArrayList<MlConversationPart>();
	}
		
	public void oddConversationPart(MlConversationPart part){
		parts.add(part);
	}
	public ArrayList<MlConversationPart> getParts() {
		return parts;
	}

	public void setParts(ArrayList<MlConversationPart> parts) {
		this.parts = parts;
	}
	
	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	
	
}
