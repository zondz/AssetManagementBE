package com.nt.rookies.asset.utils;

import com.nt.rookies.asset.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class UserFormatUtils {

	@Autowired
	private UserRepository userRepository;

	public String formatUsername(String fName, String lName){
		String userName = fName.replaceAll(" ", "").trim().toLowerCase();
		String afterStr = "";
		for (String s : lName.split(" ")) {
			afterStr+=s.charAt(0);
		}
		userName += afterStr;
		String lastIndex = "";
		if (userRepository.findLastUsername(userName) != null){
			String lastUsername = userRepository.findLastUsername(userName);
			char[] chars = lastUsername.toCharArray();
			for(char c: chars){
				if(Character.isDigit(c)){
					String lastNumb = String.valueOf(c);
					lastIndex += Integer.parseInt(lastNumb) + 1;
				}
				if(c == chars[chars.length-1] && !Character.isDigit(c)){
					lastIndex += 1;
				}
			}
		}
		return userName.toLowerCase()+lastIndex;
	}
}
