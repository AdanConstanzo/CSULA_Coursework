using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class uiManager : MonoBehaviour {

  private GameObject player;

	// Use this for initialization
	void Start () {
		player = GameObject.Find("Player");
    Debug.Log(player.GetComponent<PlayerCharacter>()._health);
    
	}
	
	// Update is called once per frame
	void Update () {
		
	}
}
