package Components;

import java.awt.Graphics2D;

import Utilities.SoundDriverHo;

public class MotherShip {

	Enemy[] zerg = new Enemy[30];
	int enemyPosition = 0;
	Ship s;
	final int timer = 30;
	int delay = 0;
	public int killedEnemies = 0;
	public int score = 0;
	private int killForBossSpawn;
	private boolean bossSpawn = false;
	private boolean bossAppear = false;
	private int bossIndex;

	public MotherShip(Ship s) {
		this.s = s;
	}

	public void draw(Graphics2D win, int difficulty) {
		for (int i = 0; i < zerg.length; i++) {
			if (zerg[i] != null) {
				zerg[i].draw(win);
			}
		}
		if (difficulty == 0) {
			this.killForBossSpawn = 10;
		} 
	}

	public Enemy[] getEnemies() {
		return zerg;

	}

	public void update(Ship s, SoundDriverHo sd) {
		// add enemies
		// create a total number of enemies per stage and use random to find which one
		// to display when
		if (this.killedEnemies == this.killForBossSpawn) {
			this.bossSpawn = true;
			this.killedEnemies = 0;
		}

		if (delay == 0) {
			if (!bossSpawn) {

				if (Math.random() > 0.8) {
					Enemy e = null;
					// determines if it is advanced
					if (Math.random() > 0.7) {
						e = new CoolEnemy(this.s);
						zerg[enemyPosition] = e;
					} else {
						e = new SimpleEnemy(this.s);
						zerg[enemyPosition] = e;
					}
				}

			} else {
				if (!bossAppear) {
					Enemy e = new Boss(this.s);
					zerg[enemyPosition] = e;
					System.out.println("made boss");
					bossAppear = true;
					if(this.killedEnemies == killForBossSpawn) {
						bossIndex = this.enemyPosition;	
					}
				}
			}

			this.enemyPosition++;
			this.enemyPosition %= this.zerg.length;

			delay = timer;
		} else {
			delay--;
		}

		// move each and every enemy

		for (int i = 0; i < zerg.length; i++) {
			if (zerg[i] != null) {
				zerg[i].update();
			}
		}

		// put in collision with the bullets/ship
		for (int j = 0; j < zerg.length; j++) {
			if (zerg[j] != null) {
				for (int k = 0; k < s.lGun.length; k++) {
					if (s.lGun[k] != null && zerg[j] != null) {
						if (zerg[j].intersectsLine(s.lGun[k].xpoints[0], s.lGun[k].ypoints[0], s.lGun[k].xpoints[1],
								s.lGun[k].ypoints[1])) {
							if (!zerg[j].isDestroyed) {
								s.lGun[k] = null;
								zerg[j].takeDamage(1);
							}
							if (!zerg[j].isDestroyed) {
								if (zerg[j].hp == 0) {
									sd.play(1);
									if (!zerg[j].isDestroyed) {
										zerg[j].deathParticle();
									}
									if (zerg[j].dpDestroyed) {
										zerg[j].noParticle();
										zerg[j] = null;
										if (j == bossIndex) {
											zerg[j].pewpew = null;
											zerg[j].boomboom = null;
										}
									}
									if (j == bossIndex) {
										score += 10;
										this.bossAppear = false;
										this.bossSpawn = false;
										bossIndex = -1;
									} else {
										killedEnemies++;
										score++;
									}
								}
							}
							break;
						}
					}

					if (s.rGun[k] != null && zerg[j] != null) {
						if (zerg[j].intersectsLine(s.rGun[k].xpoints[0], s.rGun[k].ypoints[0], s.rGun[k].xpoints[1],
								s.rGun[k].ypoints[1])) {
							if (!zerg[j].isDestroyed) {
								s.rGun[k] = null;
								zerg[j].takeDamage(1);
							}
							if (!zerg[j].isDestroyed) {
								if (zerg[j].hp == 0) {
									sd.play(1);
									if (!zerg[j].isDestroyed) {
										zerg[j].deathParticle();
									}
									if (zerg[j].dpDestroyed) {
										zerg[j].noParticle();
										zerg[j] = null;
										if (j == bossIndex) {
											zerg[j].pewpew = null;
											zerg[j].boomboom = null;
										}
									}
									if (j == bossIndex) {
										score += 10;
										this.bossAppear = false;
										this.bossSpawn = false;
										bossIndex = -1;
									} else {
										killedEnemies++;
										score++;
									}
								}
							}
							break;
						}
					}

				}

			}

		}
		// collision with the enemies and the ship

		for (int i = 0; i < zerg.length; i++) {
			if (zerg[i] != null) {
				if (zerg[i].collision(s)) {
					if (!zerg[i].collision) {
						zerg[i].collision = true;
						sd.play(1);
						if (i != bossIndex) {
							zerg[i].hp = 0;
							zerg[i].deathParticle();
							s.hp--;
						} else {
							s.hp = 0;
							System.out.println("oops I killed you!");
							System.out.println(bossIndex);
						}
					}
				} else if(zerg[i].collision) {
					if (zerg[i].dpDestroyed) {
						zerg[i].noParticle();
						zerg[i] = null;
						killedEnemies++;
						score++;
					}
				}
			}
		}

	}

}
