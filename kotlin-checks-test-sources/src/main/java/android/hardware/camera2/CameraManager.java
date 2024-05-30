package android.hardware.camera2;

import java.util.ArrayList;
import java.util.List;

public class CameraManager {
    public List<String> cameraIdList = new ArrayList<>();

    public CameraManager() {
    }

    public void setTorchMode(String cameraId, boolean enabled) {
        System.out.println("toto");
    }
}
