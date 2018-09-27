using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Teleport : MonoBehaviour {

    Vector3 destination;
    public AudioSource teleportSound;

    public void OnTriggerEnter(Collider other)
    {
        destination = new Vector3(Random.Range(-45, 45), 1, Random.Range(-45, 45));
        other.transform.position = destination;
        FindObjectOfType<AudioM>().Play("Teleport");

    }

}
