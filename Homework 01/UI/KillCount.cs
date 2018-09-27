using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;


public class KillCount : MonoBehaviour {

  public GameObject EnemyControll;

  public GameObject killCount, bystanderCount, enemeyCount;

  private npcControll _npcControll;

	// Use this for initialization
	void Start () {
		_npcControll = EnemyControll.GetComponent<npcControll>();
	}
	
	// Update is called once per frame
	void Update () {
		
	}

  void OnGUI()
  {
    killCount.GetComponent<Text>().text = "Count: "+ _npcControll.KillCount;
    bystanderCount.GetComponent<Text>().text = "Bystander: "+ _npcControll.npc_dict["Bystander"];
    enemeyCount.GetComponent<Text>().text = "Enemy: "+ _npcControll.npc_dict["Enemy"];
  }
}
