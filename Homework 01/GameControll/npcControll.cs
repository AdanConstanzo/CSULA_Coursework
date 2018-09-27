using System.Collections;
using System.Collections.Generic;
using UnityEngine;


public class npcControll : MonoBehaviour {

  public int KillCount;

  public bool PlayerIsDead;

  public bool canShoot;
  public List<GameObject> npcObjects;

  public float npcTimeSpawn;

  public GameObject floor;

  public int npcLimitCount;

  public int npcStartCount;

  public int npcShootCount;

  public List<GameObject> npcs;

  public List<GameObject> npcSpawnLocations;

  public GameObject Player;
  private PlayerCharacter playerCharacter;

  Random rnd;

  private float floorX;

  private float floorZ;

  public bool[] AvaliabeLocations;

  public bool[] AvaliabeNpc2Locations;

  private GameObject FireBallParent;

  public int npcEnemey2Index;

  public List<GameObject> npcSpawnLocations2;

  public Dictionary<string, int> npc_dict = new Dictionary<string, int>();


    void Start() {
    playerCharacter = Player.GetComponent<PlayerCharacter>();
    floorX = floor.transform.localScale.x;
    floorZ = floor.transform.localScale.z;
    FireBallParent = GameObject.Find("FireBallParent");
    canShoot = true;
    KillCount = 0;
    npcShootCount = 0;
    AvaliabeLocations = new bool[npcSpawnLocations.Count];
    AvaliabeNpc2Locations = new bool[npcSpawnLocations2.Count];
    npcEnemey2Index = -1;
    InitialSpawn(); 
  }

  void InitialSpawn()
  {
    npc_dict["Enemy"] = 0;
    npc_dict["Bystander"] = 0;
    for(int i = 0; i < npcStartCount; i++) 
    {
      SpawnRandomNpc();
    }
    Debug.Log(npc_dict["Enemy"]);
    Debug.Log(npc_dict["Bystander"]);
    // InvokeRepeating("SpawnNpcs", npcTimeSpawn, npcTimeSpawn);
  }
  void CheckIfFilled(bool[] list) {
    bool StillSpace = false;
    for (int i = 0; i < list.Length; i++) 
      if (list[i] == false)
        StillSpace = true;   
    
    if (!StillSpace)
      for(int i = 0; i < list.Length; i++)
        list[i] = false;
  }
  int GetIndexForSpawning(bool[] list) {
    CheckIfFilled(list);
    int r_num = Random.Range(0, list.Length);
    while (list[r_num] == true)
      r_num = Random.Range(0, list.Length);

    return r_num;
  }

  void SpawnRandomNpc() {
    // Random NPC Numbers
    int index = Random.Range(0,3);
    // Initalize our position vector.
    Vector3 pos;
    npcEnemey2Index = -1;
    if (index != 1) {
      // Random Spawn Location
      int npcSpawnLocationsIndex = GetIndexForSpawning(AvaliabeLocations);
      pos = npcSpawnLocations[npcSpawnLocationsIndex].transform.position;
      AvaliabeLocations[npcSpawnLocationsIndex]  = true;
      // Add our instantiated object to npcObjects array.
    } else {
      int npcSpawnLocationsIndex2 = GetIndexForSpawning(AvaliabeNpc2Locations);
      npcEnemey2Index = npcSpawnLocationsIndex2;
      pos = npcSpawnLocations2[npcSpawnLocationsIndex2].transform.position;
      AvaliabeNpc2Locations[npcSpawnLocationsIndex2]  = true;
    }
    GameObject npc = Instantiate(npcs[index], pos, transform.rotation) as GameObject;
    npcObjects.Add(npc);
    npc_dict[npc.tag] += 1;
    npcEnemey2Index = -1;
    
  }

  public void EnemeyDied()
  {
    SpawnRandomNumberOfNpcs();
  }

  void SpawnRandomNumberOfNpcs()
  {
    int randomNumber;
    int avaliableNpcs = npcLimitCount - npcObjects.Count;
    if (avaliableNpcs > 3)
      randomNumber = Random.Range(1,4);
    else
      randomNumber = Random.Range(1, avaliableNpcs + 1);
    for(int i = 0; i < randomNumber; i++)
    {
      SpawnRandomNpc();
    }
  }

  void SpawnNpcs()
  {
    if (npcObjects.Count < 10)
    {
      SpawnRandomNpc();
    }
  }

  void DeleteFireballs()
  {
    int fireBallCount = FireBallParent.transform.childCount;
    for(int i = 0; i < fireBallCount; i++)
    {
      if(FireBallParent.transform.GetChild(0) != null)
      {
        DestroyImmediate(FireBallParent.transform.GetChild(0).gameObject);
      }
    }
  }

  void Update()
  {
    if(Input.GetKeyDown(KeyCode.U))
    {
      playerCharacter.ResetHealth();
      playerCharacter.ResetPosition();
      AvaliabeLocations = null;
      AvaliabeLocations = new bool[npcSpawnLocations.Count];
      AvaliabeNpc2Locations = null; 
      AvaliabeNpc2Locations = new bool[npcSpawnLocations2.Count];
      int count = npcObjects.Count;
      for(int i = 0; i < count; i++)
      {
        Destroy(npcObjects[0]);
        npcObjects.RemoveAt(0);
      }
      // CancelInvoke("SpawnNpcs");
      npcShootCount = 0;
      PlayerIsDead = false;
      canShoot = true;
      InitialSpawn();
      DeleteFireballs();
      KillCount = 0;
    }

    if (PlayerIsDead)
    {
      DeleteFireballs();
      canShoot = false;
    }
    
  }

}
