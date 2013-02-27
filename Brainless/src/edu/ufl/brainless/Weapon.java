package edu.ufl.brainless;

public class Weapon extends Item {
	int ammoRemaining;
	final int constAmmoInClip; //keep track of original number of bullets in clip
	int ammoInClip; // changes as the gun is fired
	int numberOfClips;
	int reloadTime;	
	int weaponDamage;
	private static final String TAG = Weapon.class.getSimpleName();

	//constructors
	public Weapon(){ // default constructor
		super();
		numberOfClips = 3; //default have 3 clips
		constAmmoInClip = 10; //default ammo in clip is 10
		ammoInClip = constAmmoInClip;
		ammoRemaining = numberOfClips*ammoInClip; 		
		reloadTime = 2;
		weaponDamage = 50;		
	}	

	public Weapon(String weaponName, int constAmmoInClip, int numberOfClips,
			int reloadTime, int weaponDamage){
		super(weaponName);
		this.constAmmoInClip = constAmmoInClip;
		this.numberOfClips = numberOfClips;
		this.ammoInClip = constAmmoInClip;
		this.ammoRemaining = numberOfClips*ammoInClip;		
		this.reloadTime = reloadTime;
		this.weaponDamage = weaponDamage;
	}

	// setters and getters
	public int getAmmoRemaining() {
		return ammoRemaining;
	}

	public void setAmmoRemaining(int ammoRemaining) {
		this.ammoRemaining = ammoRemaining;
	}

	public int getAmmoInClip() {
		return ammoInClip;
	}

	public void setAmmoInClip(int ammoInClip) {
		this.ammoInClip = ammoInClip;
	}

	public int getReloadTime() {
		return reloadTime;
	}

	public void setReloadTime(int reloadTime) {
		this.reloadTime = reloadTime;
	}

	public int getWeaponDamage() {
		return weaponDamage;
	}

	public void setWeaponDamage(int weaponDamage) {
		this.weaponDamage = weaponDamage;
	}

	public int getNumberOfClips() {
		return numberOfClips;
	}

	public void setNumberOfClips(int numberOfClips) {
		this.numberOfClips = numberOfClips;
	}

	//shoot and reload methods
	public boolean shoot(){
		if (this.ammoRemaining == 0){			
			return false;
		}		
		else if(this.ammoRemaining != 0 && this.ammoInClip == 0){
			this.reload();
			return false;
		}
		else{
			//TODO implement shooting
			this.ammoRemaining --;
			this.ammoInClip --;
			if(this.ammoRemaining == 0){
				this.numberOfClips = 0;
			}
			return true;			
		}
	}	

	public void reload(){
		try {
		    Thread.sleep(reloadTime*1000); //Is this is the right way to pause for reload? Hope this doesn't pause the entire game.
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}

		this.numberOfClips --;
		this.ammoInClip = constAmmoInClip;
	}
}
