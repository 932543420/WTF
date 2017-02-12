package org.cs362.dominion;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class mainCard {
	public  static void main(String args[]){
		
		List<Card> cards=new LinkedList<Card>();
		//the cards  are achieved by each element/constant in the enum class 
		cards = new ArrayList<Card>(Card.createCards());
		
		for(Card card:cards)
			System.out.println(card.toString());
		
		for(Card card:cards)
			card.play(null,null); 
		System.exit(0);   
	}

}
