package entity;

import core.GamePanel;

public class NPC_Guide extends Entity {
	
	public NPC_Guide(GamePanel gp) {
		super(gp);
		setPackageName("npc");

		setDirection("down");
		setSpeed(0);

		getImage();
		Dialogues();

	}

	// each direction gets assigned to a sprite
	public void getImage() {
		try {

			up1 = setup("shibaNPC");
			down1 = setup("shibaNPC");
			left1 = setup("shibaNPC");
			right1 = setup("shibaNPC");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void Dialogues() {
		
		dialogues[0] = "PAPA BERGOLIO!? Sei davvero tu?";
		dialogues[1] = "Non abbiamo tempo per le domande! \n"
				+ "Sei stato ingiustamente carcerato e hai perso il tuo\ntitolo di Sua Santità.";
		dialogues[2] = "Devi affontare un viaggio per raccogliere la spada sacra\ne ristaurare l'ordine perduto!";
		dialogues[3] = "Prima di tutto dovrai superare il labirinto nella struttura\nqui vicina, la spada si trova alla fine.";
		dialogues[4] = "Sentiti libero di provare la spada sugli slime fuori dal\nlabirinto. Torna da me il prima possibile.";
		
	}
	
	public void speak() {
		
		if(dialogues[dialogueIndex] == null) {
			gp.gameState = gp.dialogueState;
			dialogueIndex = 2;
		}
		
		gp.ui.setCurrentDialogue(dialogues[dialogueIndex]);
		dialogueIndex++;
		
		facePlayerDirection();
		
		//super.speak();
	
	}
	
}
