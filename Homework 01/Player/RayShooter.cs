using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class RayShooter : MonoBehaviour {

    public GameObject EnemeyControll;

    private npcControll _npc;

    public GameObject BulletPrefab;
    public Camera _camera;

    public GameObject bulletTex;
    public GameObject bulletTex2;

    private FPSInput _fpsinput;

    public AudioClip gunsound;


    // Use this for initialization
    void Start () {
    Cursor.lockState = CursorLockMode.Locked;
    Cursor.visible = false;
    _npc = EnemeyControll.GetComponent<npcControll>();
	}

    // Update is called once per frame
    void Update()
    {

        // Create a ray that pass through the screen center.
        if (Input.GetMouseButtonDown(0) && _npc.PlayerIsDead == false)
        {
            Vector3 point = new Vector3(_camera.pixelWidth / 2,
                                        _camera.pixelHeight / 2, 0);
            Ray ray = _camera.ScreenPointToRay(point);

            _fpsinput = GetComponent<FPSInput>();

            bool isPrime = _fpsinput.getPrime();

            // Ray Cast
            RaycastHit hit;
            if (Physics.Raycast(ray, out hit))
            {
                GameObject hitObject = hit.transform.gameObject;
                if (hitObject.tag == "Enemy" )
                {
                  hitObject.GetComponent<Npc>().Hurt(1);
                    if (isPrime)
                    {
                        FindObjectOfType<AudioM>().Play("AK");
                    }
                    else
                    {
                        FindObjectOfType<AudioM>().Play("Pistol");
                    }
                    FindObjectOfType<AudioM>().Play("Hitmarker");

                  // StartCoroutine(SphereIndicator(hit.point));
                } else {
                    if (isPrime) {
                        Instantiate(bulletTex, hit.point, Quaternion.FromToRotation(Vector3.up, hit.normal));
                        FindObjectOfType<AudioM>().Play("AK");
                    } else { 
                        Instantiate(bulletTex2, hit.point, Quaternion.FromToRotation(Vector3.up, hit.normal));
                        FindObjectOfType<AudioM>().Play("Pistol");
                    }
                }
            }
        }
    }
    // Renders after update()
    private void OnGUI()
    {
        int size = 12;
        float posX = _camera.pixelWidth / 2 - size / 4;
        float posY = _camera.pixelHeight / 2 - size / 4;
        GUI.contentColor = Color.yellow;
        GUI.Label(new Rect(posX, posY, size, size), "*");
    }
    private IEnumerator SphereIndicator(Vector3 pos)
    {
        // GameObject sphere = GameObject.CreatePrimitive(PrimitiveType.Sphere);
        GameObject bullet = Instantiate(BulletPrefab) as GameObject;
        bullet.transform.position = pos;
        yield return new WaitForSeconds(1);
        Destroy(bullet);
    }
}
