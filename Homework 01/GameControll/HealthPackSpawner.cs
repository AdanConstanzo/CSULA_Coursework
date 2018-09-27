using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class HealthPackSpawner : MonoBehaviour {

    public Transform[] spawnlocations;

    public GameObject[] healthpack;

    public GameObject[] spawnClone;

    void Start()
    {
        spawn();
    }

     void Update()
    {
        if (Input.GetKeyDown(KeyCode.U))
        {
            spawn();
        }
    }

    void spawn()
    {
        spawnClone[0] = Instantiate(healthpack[0], spawnlocations[0].transform.position,Quaternion.Euler(0,0,0)) as GameObject;
        spawnClone[0] = Instantiate(healthpack[0], spawnlocations[1].transform.position, Quaternion.Euler(0, 0, 0)) as GameObject;
        spawnClone[0] = Instantiate(healthpack[0], spawnlocations[2].transform.position, Quaternion.Euler(0, 0, 0)) as GameObject;
    }
}
