package edu.ufl.brainless;

import java.util.HashMap;

public class Inventory{
	private static final String TAG = Inventory.class.getSimpleName();
	
	//Use Inventory.VARIABLE (eg getWeapon(Inventory.SHOTGUN)).
	//Update NumberOfWeapons when adding new weapons.
	private static final int NumberOfWeapons = 2;
	public static final int PISTOL = 0;
	public static final int SHOTGUN = 1;
	//public static final int WEAPON3 = 2;
	
	private Weapon[] weaponList = new Weapon[NumberOfWeapons];
	private Player player;
	private int playerEquipped = 0;
	private int UIPointer = 1;
	
	public Inventory(Player player) {
		this.player = player;
		weaponList[PISTOL] = player.getWeapon();
	}
	
	public Weapon getWeapon(int weapon) {
		return weaponList[weapon];
	}
	
	//Returns old weapon.
	public Weapon storeWeapon(int weaponType, Weapon weapon) {
		Weapon result = weaponList[weaponType];
		weaponList[weaponType] = weapon;
		return result;
	}
	
	//Stores current player weapon and switches new weapon
	public void swapWeapon(int weapon) {
		weaponList[playerEquipped] = player.getWeapon();
		player.setWeapon(weaponList[weapon]);
		playerEquipped = weapon;
	}
	
	public void menuUp() {
		//load
	}
	
	public void menuDown() {
		
	}
}
