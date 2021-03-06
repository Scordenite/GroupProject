import java.util.ArrayList;
import java.util.Stack;

public class DiscardHighPoints extends Player {
  
  /**
   * find a card to be played based on the DiscardHighPoints play strategy
   * @param lastDiscard the topmost card of the discard pile
   * @return a high point card to change suits if one exists, or the higheset
   * point card that can be played, or null if neither exist
   */
  public Card getHighPointCard(DiscardPile pile, Card lastDiscard) {
    Card highPointCard = null;
    
    //loop over the player's hand
    for(Card thisCard : hand) {
      //LOGIC: is nextCard valid? need to check if suit matches, or rank matches
      //if not valid, continue the loop. remember 8 is always valid
      if(isValid(pile,lastDiscard)){
        //the card is valid to be played
        
        //if suit is diff and it is a high point card, it qualifies as a highPointSuitChange card
        //we should return the highPointSuitChange card right away
        //suppose HighPointCard >= 10;
        if(!thisCard.getSuit().equals(lastDiscard.getSuit()) && (thisCard.getPointValue() >= 10)) {
          return thisCard;
        }
        //set first valid card to highPointCard to start
		if(highPointCard == null) {
			highPointCard = thisCard;
		}
		// check if it has a higher point value than current highPointCard
        if(thisCard.getPointValue() > highPointCard.getPointValue()){
          highPointCard = thisCard;
        }
      }
      //not valid to be played
    }
    //could not find a HighPointSuitChange, return the highest value card, or null if not found
    return highPointCard;
  }
  
  public boolean play(DiscardPile discardPile, Stack<Card> drawPile, ArrayList<Player> players) {
    //look for high point cards
    Card toPlay = getHighPointCard(discardPile,discardPile.top());
    
    if(toPlay != null) {
      //place card in discard pile and remove from the player's hand
      //if the player's hand is empty, we win! return true
      discardPile.add(toPlay);
      hand.remove(toPlay);
      // if the hand is empty we won!
      if(hand.isEmpty()){
        return true;
      }
    }
    else {
		//take cards from the draw pile until valid (but could keep drawing) or drawPile empty
        //if pile empty, skip turn (return false)
        //if this card is playable, play it right away
		while(true){
			if(drawPile.empty()){
				break;
			}
			else {
				toPlay = drawPile.pop();
				hand.add(toPlay);
				if(isValid(discardPile, toPlay)) {
					hand.remove(toPlay);
					discardPile.add(toPlay);
				}
			}
		}

    }
    
    //if we get here we must have at least one card in our hand
    return false;
  }

}