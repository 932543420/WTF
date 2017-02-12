package org.cs362.dominion;
import java.util.*;



public final class Card implements Comparable<Card>, Cloneable{
	public enum Type {
		ACTION, TREASURE, VICTORY
	}

	//To make up names.
	public enum CardName {
		/** The Treasure cards */
		Gold, Silver, Copper,
		/** The Victory cards */
		Province,Duchy,Estate,Curse,
		/** The Kingdom cards */
		Adventurer,  Ambassador, Baron, Council_Room, Cutpurse, Embargo, Feast, Gardens, Great_Hall, Mine, Minion, Smithy, Village,
	}
	/**
	 * @param enum CardName the name of the card (GOLD, SILVER, COPPER, 
	 * @param enum Type the type of the card (Victory, Treasure, or Kingdom
	 * @param int cost the cost of the card
	 * @param int score the score of the card, when the game is finished
	 * @param treasureValue  the treasure value of the treasure cards (cooper, silver, and gold cards)
	 */
	private final Type realType;
	private final CardName cardName;
	private final int cost, score, treasureValue;

	private Random ran=new Random();

	private Card(CardName cardName, Type type, int cost, int score, int treasureValue) {
		this.cost = cost;
		this.score = score;
		this.treasureValue = treasureValue;
		this.realType = type;
		this.cardName = cardName;
	}

	/**
	 * Returns the type
	 */
	public Type getType() {
		return realType;
	}

	/**
	 * Returns the cardName
	 */
	public CardName getCardName() {
		return cardName;
	}
	/**
	 * Returns the cost
	 */
	public int getCost() {
		return cost;
	}


	/**
	 * Returns the treasureValue
	 */
	public int getTreasureValue() {
		return treasureValue; 
	}
	public int score() { 
			return score; 
		}
	
	public static List<Card> createCards() {
		List<Card> ret = new LinkedList<Card>();

		/** The Treasure cards  */
		Card go = new Card(CardName.Gold, Type.TREASURE, 6, 0, 3);

		Card sl = new Card(CardName.Silver, Type.TREASURE, 3, 0, 2);

		Card co = new Card(CardName.Copper, Type.TREASURE, 0, 0, 1);


		/** The Victory cards  */ 
		Card pr = new Card(CardName.Province,Type.VICTORY, 	8, 6, 0);
		Card du = new Card(CardName.Duchy,Type.VICTORY,		5, 3, 0);
		Card es = new Card(CardName.Estate,Type.VICTORY,		2, 1, 0);
		Card cu = new Card(CardName.Curse,Type.VICTORY,		0, -1, 0);
		/** The Kingdom cards , it should more than 10 cards*/
		Card ad = new Card(CardName.Adventurer,Type.ACTION,6,0,0);
		Card sm = new Card(CardName.Smithy,Type.ACTION,4,0,0);
		Card vi = new Card(CardName.Village,Type.ACTION,3,0,0);
        Card am = new Card(CardName.Ambassador,Type.ACTION,3,0,0);
        Card ba = new Card(CardName.Baron,Type.ACTION,4,0,0);
        Card cou = new Card(CardName.Council_Room,Type.ACTION,5,0,0);
        Card cut = new Card(CardName.Cutpurse,Type.ACTION,4,0,2);
        Card em = new Card(CardName.Embargo,Type.ACTION,2,0,2);
        Card ca = new Card(CardName.Gardens,Type.ACTION,4,0,0);
        Card gr = new Card(CardName.Great_Hall,Type.ACTION,3,1,0);//
        Card mi = new Card(CardName.Mine,Type.ACTION,5,0,0);
        Card min = new Card(CardName.Minion,Type.ACTION,5,0,0);
		Card fe = new Card(CardName.Feast,Type.ACTION,4,0,0);

		ret.add(sl);
		ret.add(go);
		ret.add(co);
		ret.add(pr);
		ret.add(du);
		ret.add(es);
		ret.add(cu);
		ret.add(ad);
		ret.add(sm);
		ret.add(vi);
		ret.add(am);
		ret.add(ba);
		ret.add(cou);
		ret.add(cut);
		ret.add(em);
		ret.add(ca);
		ret.add(gr);
		ret.add(mi);
		ret.add(min);
		ret.add(fe);
        return ret;
	}
	
	public void play(Player player, GameState state) {

		switch (this.cardName) {
			case Adventurer:
				//Reveal cards from your deck until you reveal 2 Treasure cards. Put those Treasure cards into your hand and discard the other revealed cards.
				//  System.out.println("TODO Adventurer Code******************************************");
				//  System.out.println("Reveal cards from the player deck until player reveal 2 Treasure cards. " +
				//  		"Put those Treasure cards into player hand and discard the other revealed cards.");
				System.out.println("Get two treasure cards!");
				int count = 0;
				Iterator<Card> theIter = player.deck.iterator();
				while (theIter.hasNext()) {
					Card newCard = theIter.next();
					if (newCard == getCard(state.cards, CardName.Copper) || newCard == getCard(state.cards, CardName.Silver) || newCard == getCard(state.cards, CardName.Gold)) {
						count = count + 1;
						player.hand.add(newCard);
						theIter.remove();
						if (count == 2) {
							break;
						}
					} else {
						player.discard(newCard);
					}
				}
				return;
			//three draws
			case Smithy:
				System.out.println("Draw three cards");
				player.drawCard();
				player.drawCard();
				player.drawCard();
				return;

			case Village:
				//System.out.println("TODO Village Code******************************************");
				//System.out.println("The player draw +1 Card.");
				//System.out.println("The player gets +2 play Actions.");
				System.out.println("Draw one card, get two more actions.");
				player.drawCard();
				player.numActions += 2;
				return;

			//put two copy of a hand in supply, others get one
		    case Ambassador:
    		if(player.hand.size() > 0){
                Card c= player.hand.get(0);
                CardName cN = c.getCardName();
                state.gameBoard.put(getCard(player.hand, cN), state.gameBoard.get(getCard(state.cards,cN)) +1);
                for (Player p : state.players) {
                    if(p!=player) {
                            player.gain(getCard(state.cards,cN));
                        state.gameBoard.put(getCard(state.cards, cN), state.gameBoard.get(getCard(state.cards,cN)) -1);
                    }
                }
            }
                System.out.println("Reveal a card from hand. Return up to two copies of it from your hand to supply. Then each other player gains a copy of it.");
                return;

		//get one card, one more action. choose one: 4 coins, or Estate
		case Baron:
			System.out.println("Draw one card, get two more action!");
			System.out.println("get four coins or get Estate!");
			if(getCard(player.hand, CardName.Estate)!=null){
				player.discard(getCard(player.hand, CardName.Estate));
				player.coins+=4;
			}
			else{
				player.gain(getCard(state.cards, CardName.Estate));
			}

			return;

			//draw 4 cards, get one buy. others draw card
		case Council_Room:
			System.out.println("Draw four cards, get one buy. Every others draw one card!");
			for(int i =0; i<4;i++){
				player.drawCard();}
				player.numBuys+=1;
				for(Player players : state.players){
					players.drawCard();
				}
			return;

				//get two coin, discard copper
		case Cutpurse:
			System.out.println("get two coins, others discard copper!");
            player.coins+=2;
            for(Player players : state.players){
            	if(players!=player&&getCard(players.hand,CardName.Copper)!=null){
            		player.discard(getCard(player.hand, CardName.Copper));
				}
			}
			return;

            //...
		case Embargo:
			int NewCardNum = 0;
			int newRandom=ran.nextInt(10);
			System.out.println("get two coins. Discard a card.");
			System.out.println("Whenever players buy a card, get a curse card per Embargo.");
			player.coins+=2;
			//player.discard.add(getCard(player.playedCards,CardName.Embargo));
			player.playedCards.remove(getCard(player.playedCards, CardName.Embargo));

			for(Card newCard : filter(state.cards, Type.ACTION)){
				if(NewCardNum==newRandom){
					GameState.addEmbargo(newCard);
				}
			}
			return;

		case Gardens:
			//int num;
			//int victory;
			//victory = num*10;
			//no function, only a garden?
			System.out.println("For every 10 cards, get one victory!");
			System.out.println("1/10 victory point XD!");
			return;

		case Great_Hall:

            System.out.println("Draw one card, get one more action, get one victory point!");
			player.drawCard();
			player.numActions+=1;
			return;

		case Mine:
			System.out.println("discard a treasure card. Gain better card ");
			if(getCard(player.hand, CardName.Silver) != null) {
				player.hand.remove(getCard(player.hand, CardName.Silver));
				player.gain(getCard(state.cards, CardName.Gold));
			}
			else if(getCard(player.hand, CardName.Copper) != null) {
				player.hand.remove(getCard(player.hand, CardName.Copper));
				player.gain(getCard(state.cards, CardName.Silver));
			}
				return;

		case Minion:
            int ndx = ran.nextInt(2);
            if(ndx==0 || player.hand==null) {
                player.coins+=2;
            }
            else {
                //int numCards=player.hand.size();
                for(int i=0; i<player.hand.size();i++) {
                    player.discard(player.hand.get(i));
                }
                player.drawCard();
                player.drawCard();
                player.drawCard();
                player.drawCard();
                for(Player p : state.players) {
                    if(p.hand.size()>=5) {
                        for(int i=0; i<p.hand.size();i++) {
                            player.discard(p.hand.get(i));
                        }
                        player.drawCard();
                        player.drawCard();
                        player.drawCard();
                        player.drawCard();
                    }
                }
            }
            return;

		case Feast:
			System.out.println("discard this card, gain card costing up to 5.");
			player.playedCards.remove(getCard(player.playedCards, CardName.Feast));
			player.coins +=5;
			return;

		default: return;
		}
	}

	public static Card getCard(List<Card> cards,CardName cardName) {
		for(int i=0; i<cards.size();i++){
			if(cards.get(i).cardName.equals(cardName))
					return cards.get(i);
		}
		
		return null;
		
		
		
	}

	   public static List<Card> filter(Iterable<Card> cards, Type target) {
		      List<Card> out = new ArrayList<Card>();
		      for (Card c : cards)
		         if (c.getType() == target)
		            out.add(c);
		      return out;
		   }

	// this method will print out all the constant cards ENUM
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(" \n\t" + this.getCardName());
//		sb.append("-" + this.getType() + " ");
//		sb.append("\t\t Cost: " + this.cost + " ");
//		sb.append("\t\t Score: " + this.score + " ");
//		sb.append("\tTreasure Value: " + this.treasureValue);

		return sb.toString();

	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(!super.equals(obj)) return false;
		if(!(obj instanceof Card)) return false;
		if(cardName.equals(((Card) obj).cardName)) return true;

		return false;//cardName.equals(((Card) obj).cardName);
	}


	public int compareTo(Card o) {
		// TODO Auto-generated method stub
		return cardName.compareTo(o.cardName);
	}
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    } 

}
