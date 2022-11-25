package com.example.nitjsrstudenthelper.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.nitjsrstudenthelper.databinding.ActivityAboutBinding;

public class AboutActivity extends AppCompatActivity {

    ActivityAboutBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityAboutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String about= "National Institute of Technology Jamshedpur (NIT Jamshedpur or NITJSR), " +
                "is an Institute of National Importance for Technical Education located at Jamshedpur, " +
                "Jharkhand, India. Established as a Regional Institute of Technology on 15 August 1960, " +
                "it was upgraded to National Institute of Technology (NIT) on 27 December 2002 with the " +
                "status of a Deemed University. It is one of the 31 NITs in India, and as such is directly " +
                "under the control of the Ministry of Education (MHRD). It is the third in the chain of 8 NITs " +
                "established as a part of the Second Five Year Plan (1956–61) by the Government of India."+"\n\n"+

                "National Institute of Technology, Jamshedpur was founded as the Regional Institute of Technology " +
                "in 1960 by Dr. Srikrishna Sinha, the then Chief Minister of the state of Bihar (unpartitioned)." +
                " The date of 15 August, Independence Day in India, was chosen for the laying of the foundation stone." +
                " It was among the first eight Regional Engineering Colleges (RECs) established as part of the Second " +
                "Five-Year Plan (1956–1961). It now serves as the NIT for the newly carved state of Jharkhand, while NIT Patna" +
                " serves as the NIT for the state of Bihar."+"\n\n"+
                "The institute offers a Ph.D in various streams and twelve semester courses various disciplines. It offers admission" +
                " in its undergraduates courses of BTech through JEE-Main Entrance Examination, which is the pre-requisite for admission in all NITs."+
                "\n"+ "NIT Jamshedpur was ranked 79 among engineering colleges in India by the National Institutional Ranking Framework (NIRF) in 2020." +
                " Its rank slipped down to 86 in the 2021 NIRF ranking." +
                " It slipped further down to 90 in 2022.https://www.nirfindia.org/2022/EngineeringRanking.html";

        binding.aboutTv.setText(about);

    }
}