using UnityEngine;
using System.Collections;
public class Fireball : MonoBehaviour {
  public float speed = 10.0f;
  public int damage = 1;

  IEnumerator destroyMe(){
    yield return new WaitForSeconds(5.0f);
    Destroy(gameObject);
  }

  void Start()
  {
    StartCoroutine(destroyMe());
  }
  void Update() {
    transform.Translate(0, 0, speed * Time.deltaTime);
  }
  void OnTriggerEnter(Collider other) {
    PlayerCharacter player = other.GetComponent<PlayerCharacter>();
    if (player != null) 
    {
      player.Hurt(1);
    }
    Destroy(this.gameObject);
    }
}