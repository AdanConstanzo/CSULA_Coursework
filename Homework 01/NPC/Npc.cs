using UnityEngine;
using System.Collections;
public class Npc : MonoBehaviour {

  public bool isEnemey;
  private int _health;

  private GameObject _EnemeyControll;
  private npcControll _npc;

  private WanderingAi wanderingAi;
  private int index;

  public int npc2Index;

  public bool dead;

  void Awake()
  {
    _EnemeyControll = GameObject.Find("EnemyControll");
    _npc = _EnemeyControll.GetComponent<npcControll>();
    index = _npc.npcObjects.Count;
    dead = false;
    if (_npc.npcEnemey2Index != -1) {
      npc2Index = _npc.npcEnemey2Index;
    }
  }
  void Start() {
    _health = 2;
    if (isEnemey)
    {
      wanderingAi = gameObject.GetComponent<WanderingAi>();
    }
    
  }
  void playerDead()
  {
    _npc.npcObjects.RemoveAt(index);
    _npc.npc_dict[gameObject.tag] -= 1;
    if (npc2Index != -1)
      _npc.AvaliabeNpc2Locations[npc2Index] = false;
    if (wanderingAi.initializedShoot)
    {
      _npc.npcShootCount -= 1;
    }
    _npc.KillCount += 1;
    StartCoroutine(Die());
    _npc.EnemeyDied();
  }

  public void Hurt(int damage) {
    _health -= damage;
    if(_health == 0) 
    {
      playerDead();
    }
  }

  private IEnumerator Die() {
    dead = true;
    for (float f=.5f; f >= 0; f -= 0.1f) {
      this.transform.Rotate(-12, 0, 0);
      yield return new WaitForSeconds(.3f);
    }
    Destroy(this.gameObject);
  }
}