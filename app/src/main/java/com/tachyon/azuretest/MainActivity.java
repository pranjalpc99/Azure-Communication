package com.tachyon.azuretest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.azure.android.communication.calling.CallAgentOptions;
import com.azure.android.communication.calling.DeviceManager;
import com.azure.android.communication.common.CommunicationIdentifier;
import com.azure.android.communication.common.CommunicationUserIdentifier;
import com.azure.android.communication.common.CommunicationTokenCredential;
import com.azure.android.communication.calling.CallAgent;
import com.azure.android.communication.calling.CallClient;
import com.azure.android.communication.calling.StartCallOptions;
import com.azure.android.communication.common.PhoneNumberIdentifier;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private CallAgent callAgent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getAllPermissions();
        createAgent();

        // Bind call button to call `startCall`
        Button callButton = findViewById(R.id.call_button);
        callButton.setOnClickListener(l -> startCall());
    }

    /**
     * Request each required permission if the app doesn't already have it.
     */
    private void getAllPermissions() {
        // See section on requesting permissions
        String[] requiredPermissions = new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE};
        ArrayList<String> permissionsToAskFor = new ArrayList<>();
        for (String permission : requiredPermissions) {
            if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionsToAskFor.add(permission);
            }
        }
        if (!permissionsToAskFor.isEmpty()) {
            ActivityCompat.requestPermissions(this, permissionsToAskFor.toArray(new String[0]), 1);
        }
    }

    /**
     * Create the call agent for placing calls
     */
    private void createAgent() {
        // See section on creating the call agent
        String userToken = "eyJhbGciOiJSUzI1NiIsImtpZCI6IjEwMiIsIng1dCI6IjNNSnZRYzhrWVNLd1hqbEIySmx6NTRQVzNBYyIsInR5cCI6IkpXVCJ9.eyJza3lwZWlkIjoiYWNzOmQzNWZhMjQxLWZmN2QtNGVmZi04ZTBhLWFmYmI0ZTgyNDhmZl8wMDAwMDAwOC1kYTQ2LTA3MmYtMjhjNS01OTNhMGQwMGI0MWMiLCJzY3AiOjE3OTIsImNzaSI6IjE2MTU4ODczNjEiLCJpYXQiOjE2MTU4ODczNjEsImV4cCI6MTYxNTk3Mzc2MSwiYWNzU2NvcGUiOiJ2b2lwIiwicmVzb3VyY2VJZCI6ImQzNWZhMjQxLWZmN2QtNGVmZi04ZTBhLWFmYmI0ZTgyNDhmZiJ9.TSeKLYngv3S17YrugoJh-eb6D5HqD3IuMsSxvGMTdYIYqdU_qbwHGrW8K_nF9UmuG_pV8TtJKwsZp6vfQyoG-1onoJ8sgr8K_K1IgJ7L09HhfWSdSbFlCpif1_0ixBvux2SJbBMwIMJ6Uu2gZOKuea6XyybBAlFAGoXlP7hv69SMBhypb2IOzWYEoMvfSImOwx5T_J9vbdTEBF_UaK0ieK5wCJi6g9fGTzYhlG8pUO3avaCbCn-nsftfzOgNkpPVzWcvZK0le8RHS_gjcTddoZuBaFZ_0RE5nJoFrr5jBFG6u-58vGQ-Zj4tqyS0svwcbFAHycH37M34CY8FmUZZLQ";

        try {
            CallClient callClient = new CallClient();
            CommunicationTokenCredential credential = new CommunicationTokenCredential(userToken);
            Context context = this.getApplicationContext();
            CallAgentOptions callAgentOptions = new CallAgentOptions();
            callAgentOptions.setDisplayName("Alice Bob");
            callAgent = new CallClient().createCallAgent(context, credential,callAgentOptions).get();
            DeviceManager deviceManager = callClient.getDeviceManager().get();
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), "Failed to create call agent.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Place a call to the callee id provided in `callee_id` text input.
     */
    private void startCall() {
        // See section on starting the call
        EditText calleeIdView = findViewById(R.id.callee_id);
        String calleeId = calleeIdView.getText().toString();

        PhoneNumberIdentifier acsUser2 = new PhoneNumberIdentifier("+917506133234");
        CommunicationIdentifier participants[] = new CommunicationIdentifier[]{ acsUser2 };

        StartCallOptions options = new StartCallOptions();

        callAgent.startCall(
                getApplicationContext(),
                participants,
                options);
    }
}