package handtypes;

import java.util.ArrayList;
import java.util.Collections;
import poker.Card;

public class CartaAlta extends Hand{

    public CartaAlta(ArrayList<Card> cards) {
        super(cards);
        Collections.sort(cards);
    }
    
    @Override
    public double getPoints() {
        double points = 0;
        double i = 1;
        for (Card card: this.cards)
        {
            points = points + (double)card.getValue() / (double)Math.pow(100, i);
            i += 1;
        }
        return points;
    }
    
    @Override
    public String toString()
    {
        return "Carta Alta: " + Card.getValueName(cards.get(0).getValue());
    }
}