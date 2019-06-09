package com.example.arval2;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;

import com.google.ar.core.Anchor;
import com.google.ar.core.Frame;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.core.Pose;
import com.google.ar.core.Session;
import com.google.ar.core.TrackingState;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.ArSceneView;
import com.google.ar.sceneform.FrameTime;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.Scene;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

import java.util.Collection;

public class MainActivity extends AppCompatActivity {


private ArFragment arFragment;
private AnchorNode anchorNode;
private boolean isModeled = false;
    private ModelRenderable mon_modele;
    private void onUpdate(FrameTime frameTime){

        if(isModeled==true){
            return;
        }
        Frame frame = arFragment.getArSceneView().getArFrame();
        Collection<Plane> planes = frame.getUpdatedTrackables(Plane.class);
        for (Plane plane : planes){
            if(plane.getTrackingState() == TrackingState.TRACKING){
               // Anchor anchor = plane.createAnchor((plane.getCenterPose()));

                if (arFragment.getArSceneView().getArFrame() == null) {
                    return;
                }

                // If ARCore is not tracking yet, then don't process anything.
                if (arFragment.getArSceneView().getArFrame().getCamera().getTrackingState() != TrackingState.TRACKING) {
                    return;
                }
               // Session session = arFragment.getArSceneView().getSession();

               // float[] pos = { 0,0, };
                //float[] rotation = {0,0,0,1};
                Anchor anchor =  plane.createAnchor(plane.getCenterPose());

                anchorNode = new AnchorNode(anchor);
                anchorNode.setRenderable(mon_modele);
                anchorNode.setParent(arFragment.getArSceneView().getScene());
                isModeled = true;



                break;
            }
        }
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.ux_fragment);

        arFragment.getArSceneView().getScene().setOnUpdateListener(this::onUpdate);

        ModelRenderable.builder()
                .setSource(this, Uri.parse("01Alocasia_obj.sfb"))
                .build()
                .thenAccept(renderable -> mon_modele = renderable);
/*
        arFragment.setOnTapArPlaneListener(
                (HitResult hitResult, Plane plane, MotionEvent motionEvent) -> {
                    if (mon_modele == null) {
                        return;
                    }
                    if (plane.getType() != Plane.Type.HORIZONTAL_UPWARD_FACING) {
                        return;
                    }


                    //On créé le point d'encrage du modèle 3d


                    Anchor anchor = hitResult.createAnchor();
                    AnchorNode anchorNode = new AnchorNode(anchor);
                    anchorNode.setParent(arFragment.getArSceneView().getScene());

                    //On attache ensuite notre modèle au point d'encrage
                    TransformableNode Node = new TransformableNode(arFragment.getTransformationSystem());
                    Node.setParent(anchorNode);
                    Node.setRenderable(mon_modele);
                    Node.select();

                    TransformableNode sun = new TransformableNode(arFragment.getTransformationSystem());
                    sun.setParent(Node);
                    sun.setLocalPosition(new Vector3(0,0,-34));
                    sun.setRenderable(mon_modele);

                    TransformableNode repere1 = new TransformableNode(arFragment.getTransformationSystem());
                    repere1.setParent(sun);
                    repere1.setLocalPosition(new Vector3(-30,0,0));
                    repere1.setRenderable(mon_modele);

                }
        );


*/


            // If there is no frame then don't process anything.


            // Place the anchor 1m in front of the camera if anchorNode is null.
            //if (this.anchorNode == null) {




           // }
        }
    }

