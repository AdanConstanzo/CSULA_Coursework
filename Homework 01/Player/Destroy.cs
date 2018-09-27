using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Destroy : MonoBehaviour {

    public void Awake()
    {
        Destroy(gameObject, 5);
    }

    // Update is called once per frame
    void Update () {
		
	}
}
