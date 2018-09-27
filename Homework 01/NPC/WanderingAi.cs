using UnityEngine;
using System.Collections;
public class WanderingAi : MonoBehaviour
{
    [SerializeField] private GameObject fireballPrefab;
    private GameObject _fireball;
    public float speed = 3.0f;

    private GameObject Player;

    public bool inRange;

    public bool reloading;

    public int inRangeDistance;

    public bool wandering;

    private GameObject _EnemeyControll;

    private npcControll _npc;

    private GameObject FireBallParent;

    public bool bystander;

    public float obstacleRange = 5.0f;

    public bool initializedShoot;


    private Npc npc;

    public bool idle;

    void Start()
    {
      Player = GameObject.Find("Player");
      inRange = false;
      _EnemeyControll = GameObject.Find("EnemyControll");
      _npc = _EnemeyControll.GetComponent<npcControll>();
      FireBallParent = GameObject.Find("FireBallParent");
      reloading = false;
      initializedShoot = false;
      npc = gameObject.GetComponent<Npc>();
    }

    void IdelCasting() {
      Ray ray = new Ray(transform.position, transform.forward);
      RaycastHit hit;

      if (Physics.SphereCast(ray, 0.75f, out hit)) {
        if (hit.distance < obstacleRange) {
          float angle = Random.Range(-110, 110);
          transform.Rotate(0, angle, 0);
        }
      }
    }

    void CalculatedCasting(float angle) {
      Ray ray = new Ray(transform.position, transform.forward);
      RaycastHit hit;

      if (Physics.SphereCast(ray, 0.75f, out hit)) {
        if (hit.distance < obstacleRange) {
          transform.Rotate(0, angle, 0);
        }
      }
    }

    IEnumerator reloadCount()
    {
      yield return new WaitForSeconds(0.25f);
      reloading = false;
    }

    void shoot()
    {
      _fireball = Instantiate(fireballPrefab) as GameObject;
      _fireball.transform.position = transform.TransformPoint(Vector3.forward * 1.5f);
      _fireball.transform.rotation = transform.rotation;
      _fireball.transform.SetParent(FireBallParent.transform);
      reloading = true;
      StartCoroutine(reloadCount());
    }

    bool InitiRayCast()
    {
      Ray ray = new Ray(transform.position, transform.forward);
      RaycastHit hit;
      if (Physics.SphereCast(ray, 3f, out hit))
      {
        if (hit.transform.tag == "Player")
        {
          return true;
        }
      }
      return false; 
    }

    bool IsRayCastingPlayer(Transform transform)
    {
        Ray ray = new Ray(transform.position, transform.forward);
        RaycastHit hit;
        if (Physics.SphereCast(ray, 3f, out hit))
        {
            if (hit.transform.tag == "Player" || hit.transform.tag == "Projectile" )
            {
                return true;
            }
        }
        return false; 
    }

    void Update()
    {
       if (bystander) {
            if (idle)
            {

                Ray ray = new Ray(transform.position, transform.forward);
                RaycastHit hit;
                if (Physics.SphereCast(ray, 0.75f, out hit) && hit.transform.tag == "Player")
                {
                    idle = false;
                }

                transform.Rotate(0, 1, 0);

            }
            else { 
              transform.Translate(0, 0, speed * Time.deltaTime);
              IdelCasting();
            }
        }else {
          // boolean to check if in range.  
          inRange = (Vector3.Distance(transform.position, Player.transform.position) < inRangeDistance);
          if (npc.dead)
            return;
            
          if (inRange) {
            // gets rotation position.
            Quaternion CurrentDirection = transform.rotation;
            // make this gameobject look at player
            gameObject.transform.LookAt(Player.transform);
            // Keep direction of player. 
            Quaternion PlayerDirection = transform.rotation;
            bool LookingAtPlayer = IsRayCastingPlayer(gameObject.transform);
            transform.rotation = CurrentDirection;
            // check to see if player is looking at player.
            if (LookingAtPlayer) {
              gameObject.transform.LookAt(Player.transform);
              // checks if npc is allowed to shoot
              if (initializedShoot) {
                // shoots when not reloading
                if (!reloading && _npc.canShoot)
                  shoot();
              } else {
                // check to see if possible to shoot.
                if (_npc.npcShootCount < 3) {
                  initializedShoot = true;
                  _npc.npcShootCount += 1;
                  shoot();
                }
              }
            } else {
              // Can't see player? Idel.
              IdelCasting();
              // Was shooting? now remove shooting.
              if (initializedShoot) {
                initializedShoot = false;
                _npc.npcShootCount -= 1;
              }
              //CalculatedCasting(CurrentDirection.z - PlayerDirection.z);
            }
          } else {
            // Not in range? just go back to Idel.
            IdelCasting();
            if (initializedShoot) {
              initializedShoot = false;
              _npc.npcShootCount -= 1;
            }
          }
          
          if (wandering) {
            transform.Translate(0, 0, speed * Time.deltaTime);
          }
        }
    }
}