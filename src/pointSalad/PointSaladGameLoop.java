package pointSalad;

import java.util.ArrayList;

import card.ICard;
import pile.IPile;
import player.IPlayer;

public class PointSaladGameLoop implements IGameLoop{
	ISetup newSaladSetup;
	
	public PointSaladGameLoop(ISetup newSaladSetup) {
		this.newSaladSetup = newSaladSetup;
	}
	
	public void gameLoop() {
		// Set random starting player
				int currentPlayer = (int) (Math.random() * (newSaladSetup.getPlayers().size()));
				boolean keepPlaying = true;

				while(keepPlaying) {
					IPlayer thisPlayer = newSaladSetup.getPlayers().get(currentPlayer);
					boolean stillAvailableCards = false;
					for(IPile p: newSaladSetup.getPile()) {
						if(!p.isEmpty()) {
							stillAvailableCards = true;
							break;
						}
					}
					if(!stillAvailableCards) {
						keepPlaying = false;
						break;
					}
					if(!thisPlayer.isBot()) {
						System.out.println("Passed is-bot check");
						thisPlayer.sendMessage("\n\n****************************************************************\nIt's your turn! Your hand is:\n");
						thisPlayer.sendMessage(newSaladSetup.getView().displayHand(thisPlayer.getHand()));
						thisPlayer.sendMessage("\nThe piles are: ");
					
						thisPlayer.sendMessage(newSaladSetup.getView().printMarket(newSaladSetup.getPile()));
						boolean validChoice = false;
						while(!validChoice) {
							thisPlayer.sendMessage("\n\nTake either one point card (Syntax example: 2) or up to two vegetable cards (Syntax example: CF).\n");
							String pileChoice = thisPlayer.readMessage();
							if(pileChoice.matches("\\d")) {
								int pileIndex = Integer.parseInt(pileChoice);
								if(newSaladSetup.getPile().get(pileIndex).getPointCard(newSaladSetup.getPile()) == null) {
									thisPlayer.sendMessage("\nThis pile is empty. Please choose another pile.\n");
									continue;
								} else {
									thisPlayer.getHand().add(newSaladSetup.getPile().get(pileIndex).buyPointCard(newSaladSetup.getPile()));
									thisPlayer.sendMessage("\nYou took a card from pile " + (pileIndex) + " and added it to your hand.\n");
									validChoice = true;
								}
							} else {
								int takenVeggies = 0;
								for(int charIndex = 0; charIndex < pileChoice.length(); charIndex++) {
									if(Character.toUpperCase(pileChoice.charAt(charIndex)) < 'A' || Character.toUpperCase(pileChoice.charAt(charIndex)) > 'F') {
										thisPlayer.sendMessage("\nInvalid choice. Please choose up to two veggie cards from the market.\n");
										validChoice = false;
										break;
									}
									int choice = Character.toUpperCase(pileChoice.charAt(charIndex)) - 'A';
									int pileIndex = (choice == 0 || choice == 3) ? 0 : (choice == 1 || choice == 4) ? 1 : (choice == 2 || choice == 5) ? 2:-1;
									int veggieIndex = (choice == 0 || choice == 1 || choice == 2) ? 0 : (choice == 3 || choice == 4 || choice == 5) ? 1 : -1;
									if(newSaladSetup.getPile().get(pileIndex).getVeggieCard(veggieIndex) == null) {
										thisPlayer.sendMessage("\nThis veggie is empty. Please choose another pile.\n");
										validChoice = false;
										break;
									} else {
										if(takenVeggies == 2) {
											validChoice = true;
											break;
										} else {
											thisPlayer.getHand().add(newSaladSetup.getPile().get(pileIndex).buyVeggieCard(veggieIndex, newSaladSetup.getPile()));
											takenVeggies++;
											//thisPlayer.sendMessage("\nYou took a card from pile " + (pileIndex) + " and added it to your hand.\n");
											validChoice = true;
										}
									}
								}

							}
						}
						//Check if the player has any criteria cards in their hand
						boolean criteriaCardInHand = false;
						for(ICard card : thisPlayer.getHand()) {
							if(card.getCriteriaSideUp()) {
								criteriaCardInHand = true;
								break;
							}
						}
						if(criteriaCardInHand) {
							//Give the player an option to turn a criteria card into a veggie card
							thisPlayer.sendMessage("\n"+newSaladSetup.getView().displayHand(thisPlayer.getHand())+"\nWould you like to turn a criteria card into a veggie card? (Syntax example: n or 2)");
							String choice = thisPlayer.readMessage();
							if(choice.matches("\\d")) {
								int cardIndex = Integer.parseInt(choice);
								ICard selectedCard = thisPlayer.getHand().get(cardIndex);
								selectedCard.setCriteriaSideUp(false);
							}
						}
						thisPlayer.sendMessage("\nYour turn is completed\n****************************************************************\n\n");
						newSaladSetup.getNetwork().sendToAllPlayers("Player " + thisPlayer.getPlayerID() + "'s hand is now: \n"+newSaladSetup.getView().displayHand(thisPlayer.getHand())+"\n", newSaladSetup.getPlayers());	

					} else {
						// Bot logic
						// The Bot will randomly decide to take either one point card or two veggie cards 
						// For point card the Bot will always take the point card with the highest score
						// If there are two point cards with the same score, the bot will take the first one
						// For Veggie cards the Bot will pick the first one or two available veggies
						boolean emptyPiles = false;
						// Random choice: 
						int choice = (int) (Math.random() * 2);
						if(choice == 0) {
							// Take a point card
							int highestPointCardIndex = 0;
							int highestPointCardScore = 0;
							for(int i = 0; i < newSaladSetup.getPile().size(); i++) {
								if(newSaladSetup.getPile().get(i).getPointCard(newSaladSetup.getPile()) != null) {
									ArrayList<ICard> tempHand = new ArrayList<ICard>();
									for(ICard handCard : thisPlayer.getHand()) {
										tempHand.add(handCard);
									}
									tempHand.add(newSaladSetup.getPile().get(i).getPointCard(newSaladSetup.getPile()));
									int score = newSaladSetup.getScoreCalculator().calculateScore(tempHand, thisPlayer, newSaladSetup.getPlayers());
									if(score > highestPointCardScore) {
										highestPointCardScore = score;
										highestPointCardIndex = i;
									}
								}
							}
							if(newSaladSetup.getPile().get(highestPointCardIndex).getPointCard(newSaladSetup.getPile()) != null) {
								thisPlayer.getHand().add(newSaladSetup.getPile().get(highestPointCardIndex).buyPointCard(newSaladSetup.getPile()));
							} else {
								choice = 1; //buy veggies instead
								emptyPiles = true;
							}
						} else if (choice == 1) {
							// TODO: Check what Veggies are available and run calculateScore to see which veggies are best to pick
							int cardsPicked = 0;
							for(IPile pile : newSaladSetup.getPile()) {
								if(pile.getVeggieCard(0) != null && cardsPicked < 2) {
									thisPlayer.getHand().add(pile.buyVeggieCard(0, newSaladSetup.getPile()));
									cardsPicked++;
								}
								if(pile.getVeggieCard(1) != null && cardsPicked < 2) {
									thisPlayer.getHand().add(pile.buyVeggieCard(1, newSaladSetup.getPile()));
									cardsPicked++;
								}
							}
							if(cardsPicked == 0 && !emptyPiles) {
								// Take a point card instead of veggies if there are no veggies left
								int highestPointCardIndex = 0;
								int highestPointCardScore = 0;
								for(int i = 0; i < newSaladSetup.getPile().size(); i++) {
									if(newSaladSetup.getPile().get(i).getPointCard(newSaladSetup.getPile()) != null && newSaladSetup.getPile().get(i).getPointCard(newSaladSetup.getPile()).getCriteriaSideUp()) {
										ArrayList<ICard> tempHand = new ArrayList<ICard>();
										for(ICard handCard : thisPlayer.getHand()) {
											tempHand.add(handCard);
										}
										tempHand.add(newSaladSetup.getPile().get(i).getPointCard(newSaladSetup.getPile()));
										int score = newSaladSetup.getScoreCalculator().calculateScore(tempHand, thisPlayer, newSaladSetup.getPlayers());
										if(score > highestPointCardScore) {
											highestPointCardScore = score;
											highestPointCardIndex = i;
										}
									}
								}
								if(newSaladSetup.getPile().get(highestPointCardIndex).getPointCard(newSaladSetup.getPile()) != null) {
									thisPlayer.getHand().add(newSaladSetup.getPile().get(highestPointCardIndex).buyPointCard(newSaladSetup.getPile()));
								}
							}
						}
						newSaladSetup.getNetwork().sendToAllPlayers("Bot " + thisPlayer.getPlayerID() + "'s hand is now: \n"+newSaladSetup.getView().displayHand(thisPlayer.getHand())+"\n", newSaladSetup.getPlayers());
					}
					
					if(currentPlayer == newSaladSetup.getPlayers().size()-1) {
						currentPlayer = 0;
					} else {
						currentPlayer++;
					}
				}
				newSaladSetup.getNetwork().sendToAllPlayers(("\n-------------------------------------- CALCULATING SCORES --------------------------------------\n"), newSaladSetup.getPlayers());
				for(IPlayer player : newSaladSetup.getPlayers()) {
					newSaladSetup.getNetwork().sendToAllPlayers("Player " + player.getPlayerID() + "'s hand is: \n"+newSaladSetup.getView().displayHand(player.getHand()), newSaladSetup.getPlayers());
					player.setScore(newSaladSetup.getScoreCalculator().calculateScore(player.getHand(), player, newSaladSetup.getPlayers())); 
					newSaladSetup.getNetwork().sendToAllPlayers("\nPlayer " + player.getPlayerID() + "'s score is: " + player.getScore(), newSaladSetup.getPlayers());
				}

				int maxScore = 0;
				int playerID = 0;
				for(IPlayer player : newSaladSetup.getPlayers()) {
					if(player.getScore() > maxScore) {
						maxScore = player.getScore();
						playerID = player.getPlayerID();
					}
				}
				for(IPlayer player : newSaladSetup.getPlayers()) {
					if(player.getPlayerID() == playerID) {
						player.sendMessage("\nCongratulations! You are the winner with a score of " + maxScore);
					} else {
						player.sendMessage("\nThe winner is player " + playerID + " with a score of " + maxScore);
					}
				}
			}
	}
