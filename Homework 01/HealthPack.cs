using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class HealthPack : MonoBehaviour {


	void Start () {
		
	}
	

	void Update () {

    }

    void OnTriggerEnter(Collider other)
    {
        PlayerCharacter player = other.GetComponent<PlayerCharacter>();
        if (player != null)
        {
            player.HealthPack(100);
            FindObjectOfType<AudioM>().Play("Heal");
        }
        Destroy(this.gameObject);

    }

}
