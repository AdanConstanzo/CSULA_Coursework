using UnityEngine;
using System.Collections;
public class PlayerCharacter : MonoBehaviour {
  public int _health;
  private int _maxHealth;

  private GameObject EnemyControll;

  private npcControll _npcControll;

  public GameObject healthBar;
  void Start() {
    EnemyControll = GameObject.Find("EnemyControll");
    _npcControll = EnemyControll.GetComponent<npcControll>();
    _maxHealth = _health;
  }



  /*
  When the player’s health is 0, stop the game. The program should stop processing player moves,
player shooting, and enemy’s attack. Player’s rotation and enemy’s moves must be allowed.
   */
  void Dead() 
  {
    _npcControll.PlayerIsDead = true;
  }

  public void ResetHealth()
  {
    _health = _maxHealth;
    healthBar.transform.localScale = new Vector3(1,1,1);
  }

  public void ResetPosition()
  {
    transform.position = new Vector3(42,2,-46);
  }

  public void Hurt(int damage) {
    _health -= damage;
    float healthBarValue = (float)_health/(float)_maxHealth;

    if(_health >= 0)
    {
      healthBar.transform.localScale = new Vector3( healthBarValue ,1,1);
    }    

    if (_health == 0) {
      Dead();
    }
  }

    public void HealthPack(int health)
    {

        if (_health + health > _maxHealth)
        {
            _health = _maxHealth;
        } else { 
            _health += health;
        }

        float healthBarValue = (float)_health / (float)_maxHealth;

        if (_health >= 0)
        {
            healthBar.transform.localScale = new Vector3(healthBarValue, 1, 1);
        }

    }
}