package com.p1nero.epicfightbow.gameassets;

import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.collider.MultiOBBCollider;

public class EFBowColliders {
    public static final Collider BOW_DASH = new MultiOBBCollider(2, 1, 1.5, 1, 0, 0, 0);
    public static final Collider BOW_ELBOW = new MultiOBBCollider(2, 1, 1, 1, 0, 1, 0);
    public static final Collider BOW_SCAN = new MultiOBBCollider(2, 8, 4D, 8, 0.0D, 1, -8.5);
}
