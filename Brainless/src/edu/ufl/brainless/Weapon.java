package edu.ufl.brainless;

public class Weapon extends Item {
	int ammoRemaining;
	int ammoInClip;
	int reloadTime;	
	int weaponDamage;
	private static final String TAG = Weapon.class.getSimpleName();
	
	//constructors
	public Weapon(){ // default constructor
		super();
		ammoRemaining = 3*ammoInClip; //default have 3 clips
		ammoInClip = 10;
		reloadTime = 2;
		weaponDamage = 50;		
	}
	
	public Weapon(String weaponName, int ammoRemaining, int ammoInClip, int reloadTime, int weaponDamage){
		super(weaponName);
		this.ammoRemaining = ammoRemaining;
		this.ammoInClip = ammoInClip;
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
}
