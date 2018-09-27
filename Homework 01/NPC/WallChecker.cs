using UnityEngine;
using System.Collections;
public class WallChecker : MonoBehaviour
{
  public GameObject  wall;
  BoxCollider _c_e, _c_w;
  void Start()
  {
    _c_e = gameObject.GetComponent<BoxCollider>();
    _c_w = wall.GetComponent<BoxCollider>();
    if (_c_e.bounds.Intersects(_c_w.bounds))
    {
      Debug.Log("IT COLIDED YO");
    }
  }

  void onTrigger(Collider other)
  {
    Debug.Log(other);
  }

}