using UnityEngine;
using System.Collections;

// basic WASD-style movement control
// commented out line demonstrates that transform.Translate instead of charController.Move doesn't have collision detection

[RequireComponent(typeof(CharacterController))]
[AddComponentMenu("Control Script/FPS Input")]
public class FPSInput : MonoBehaviour {
	public float speed = 6.0f;
	public float gravity = -9.8f;

	private CharacterController _charController;

  private npcControll _npc;

  public GameObject gun1;
  
  public GameObject gun2;

  private bool isPrime = true;
	
	void Start() {
		_charController = GetComponent<CharacterController>();
    _npc = GameObject.Find("EnemyControll").GetComponent<npcControll>();
	}
	
  public IEnumerator changeGun() {
	  if (isPrime) 
	  {
		  gun1.SetActive(false);
		  gun2.SetActive(true);
		  isPrime = false;
	  } else
	  {
		  gun1.SetActive(true);
		  gun2.SetActive(false);
		  isPrime = true;
	  }
	  yield return new WaitForSeconds(0.25f);
  }

    public bool getPrime()
    {
        return isPrime;
    }

	void Update() {
		//transform.Translate(Input.GetAxis("Horizontal") * speed * Time.deltaTime, 0, Input.GetAxis("Vertical") * speed * Time.deltaTime);
		float deltaX = Input.GetAxis("Horizontal") * speed;
		float deltaZ = Input.GetAxis("Vertical") * speed;
		Vector3 movement = new Vector3(deltaX, 0, deltaZ);
		movement = Vector3.ClampMagnitude(movement, speed);

		movement.y = gravity;

		movement *= Time.deltaTime;
		movement = transform.TransformDirection(movement);
    
    if (_npc.PlayerIsDead == false)
    {
       _charController.Move(movement);
    }


		if (Input.GetMouseButtonDown(1) && _npc.PlayerIsDead == false) 
		{
			StartCoroutine(changeGun());
		}

	}

}
