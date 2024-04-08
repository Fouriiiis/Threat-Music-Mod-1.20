package dev.fouriiiis.threatmusicmod;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;

import java.util.LinkedList;
import java.util.Queue;

public class KeyInputHandler {
    private static final int MAX_HISTORY = 5;
    private static boolean isHandlerRegistered = false;

    // Queues for storing the state for the last 5 ticks
    private static Queue<Integer> vxHistory = new LinkedList<>();
    private static Queue<Integer> vyHistory = new LinkedList<>();
    private static Queue<Boolean> vpHistory = new LinkedList<>();
    private static Queue<Boolean> vjHistory = new LinkedList<>();
    private static Queue<Boolean> vtHistory = new LinkedList<>();

    public static void registerKeyInputs() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client != null && !isHandlerRegistered) {
                MinecraftClient minecraftClient = MinecraftClient.getInstance();
                if (minecraftClient != null && minecraftClient.options != null) {
                    isHandlerRegistered = true;
                }
            }

            if (isHandlerRegistered) {
                MinecraftClient minecraftClient = MinecraftClient.getInstance();

                // Retrieve the key bindings
                KeyBinding forwardKey = minecraftClient.options.forwardKey;
                KeyBinding backKey = minecraftClient.options.backKey;
                KeyBinding leftKey = minecraftClient.options.leftKey;
                KeyBinding rightKey = minecraftClient.options.rightKey;
                KeyBinding jumpKey = minecraftClient.options.jumpKey;
                KeyBinding useKey = minecraftClient.options.useKey;
                KeyBinding attackKey = minecraftClient.options.attackKey;
                KeyBinding changeItemKey = minecraftClient.options.swapHandsKey;

                // Calculate the variations for each category
                int vx = (rightKey.isPressed() ? 1 : 0) - (leftKey.isPressed() ? 1 : 0);
                int vy = (forwardKey.isPressed() ? 1 : 0) - (backKey.isPressed() ? 1 : 0);
                boolean vp = changeItemKey.isPressed();
                boolean vj = jumpKey.isPressed();
                boolean vt = useKey.isPressed() || attackKey.isPressed();

                // Update the history queues
                updateHistory(vxHistory, vx);
                updateHistory(vyHistory, vy);
                updateBooleanHistory(vpHistory, vp);
                updateBooleanHistory(vjHistory, vj);
                updateBooleanHistory(vtHistory, vt);

                //System.out.println("vx: " + vxHistory);
            }
        });
    }

    private static void updateHistory(Queue<Integer> history, int value) {
        if (history.size() >= MAX_HISTORY) {
            history.poll();
        }
        history.offer(value);
    }

    private static void updateBooleanHistory(Queue<Boolean> history, boolean value) {
        if (history.size() >= MAX_HISTORY) {
            history.poll();
        }
        history.offer(value);
    }

    public static void register() {
        registerKeyInputs();
    }

    public static float getVx() {
        if (vxHistory == null || vxHistory.isEmpty()) {
            return 0f; // or any default value you prefer
        }
        int count = 0;
        int last = vxHistory.peek();
        for (int i : vxHistory) {
            if (i != last) {
                count++;
            }
        }
        return count * 2f;
    }

    public static float getVy() {
        if (vyHistory == null || vyHistory.isEmpty()) {
            return 0f; // or any default value you prefer
        }
        int count = 0;
        int last = vyHistory.peek();
        for (int i : vyHistory) {
            if (i != last) {
                count++;
            }
        }
        return count * 2f;
    }

    public static float getVp() {
        if (vpHistory == null || vpHistory.isEmpty()) {
            return 0f; // or any default value you prefer
        }
        int count = 0;
        boolean last = vpHistory.peek();
        for (boolean i : vpHistory) {
            if (i != last) {
                count++;
            }
        }
        return count * 2f;
    }

    public static float getVj() {
        if (vjHistory == null || vjHistory.isEmpty()) {
            return 0f; // or any default value you prefer
        }
        int count = 0;
        boolean last = vjHistory.peek();
        for (boolean i : vjHistory) {
            if (i != last) {
                count++;
            }
        }
        return count * 2f;
    }

    public static float getVt() {
        if (vtHistory == null || vtHistory.isEmpty()) {
            return 0f; // or any default value you prefer
        }
        int count = 0;
        boolean last = vtHistory.peek();
        for (boolean i : vtHistory) {
            if (i != last) {
                count++;
            }
        }
        return count * 2f;
    }

    public static float getSv() {
        //Sv = Vx + Vy + Vp + 2(Vj + Vt)
        return getVx() + getVy() + getVp() + 2f * (getVj() + getVt());
    }
}
