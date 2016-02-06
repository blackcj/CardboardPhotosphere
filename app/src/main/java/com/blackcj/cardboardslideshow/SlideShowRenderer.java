package com.blackcj.cardboardslideshow;

import android.content.Context;

import com.google.vrtoolkit.cardboard.HeadTransform;

import org.rajawali3d.cardboard.RajawaliCardboardRenderer;
import org.rajawali3d.materials.Material;
import org.rajawali3d.materials.textures.ATexture;
import org.rajawali3d.materials.textures.Texture;
import org.rajawali3d.math.vector.Vector3;
import org.rajawali3d.primitives.Sphere;

import javax.microedition.khronos.opengles.GL10;

public class SlideShowRenderer extends RajawaliCardboardRenderer {

    private Sphere mSphere;
    private Texture mTexture;

    public SlideShowRenderer(Context context) {
        super(context);
    }

    @Override
    protected void initScene() {

        mTexture = new Texture("photo" + R.drawable.demo2_full, imageResources[index]);
        mTexture.shouldRecycle(true);
        mSphere = createPhotoSphereWithTexture(mTexture);

        getCurrentScene().addChild(mSphere);
        getCurrentCamera().setPosition(Vector3.ZERO);
        getCurrentCamera().setFieldOfView(75);
    }

    private int index = 0;
    private int[] imageResources = {R.drawable.demo10_full, R.drawable.demo11_full, R.drawable.demo1_full, R.drawable.demo3_full, R.drawable.demo4_full, R.drawable.demo5_full, R.drawable.demo6_full};

    public void changeTexture() {
        index ++;
        index = index % imageResources.length;
        if (mTexture != null) {
            mSphere.getMaterial().unbindTextures();
            mSphere.getMaterial().removeTexture(mTexture);
            mTexture = null;
        }
        mTexture = new Texture("texture", imageResources[index]);
        mTexture.shouldRecycle(true);
        try {
            mSphere.getMaterial().addTexture(mTexture);
        } catch (ATexture.TextureException e) {
            throw new RuntimeException(e);
        }
    }

    private Sphere createPhotoSphereWithTexture(ATexture texture) {
        //R.drawable.video_full
        Material material = new Material();
        material.setColor(0);

        try {
            material.addTexture(texture);
        } catch (ATexture.TextureException e) {
            throw new RuntimeException(e);
        }

        Sphere sphere = new Sphere(50, 64, 32);
        sphere.setScaleX(-1);

        sphere.setMaterial(material);

        return sphere;
    }

    double speed = 0.05;
    double sphereRotation = 0;

    @Override
    public void onNewFrame(HeadTransform headTransform) {
        super.onNewFrame(headTransform);

        sphereRotation += speed;

        // START: Used for testing in the simulator

        double range = 70.0;
        double verticalAngle = sphereRotation % range;
        if (verticalAngle > range / 2) {
            verticalAngle = range / 2 - (verticalAngle - range / 2);
        }
        // You can't apply two modifications in separate functions
        //mSphere.setRotation(0.0, sphereRotation, verticalAngle);

        // This doesn't work
        // mSphere.setRotY(sphereRotation);
        // mSphere.setRotZ(verticalAngle);
        // END
    }

    @Override public void onRenderFrame(GL10 gl) {

        super.onRenderFrame(gl);

    }
}
