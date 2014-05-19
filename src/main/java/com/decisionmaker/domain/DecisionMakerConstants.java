package com.decisionmaker.domain;

public final class DecisionMakerConstants {

	public final static String TARGET_DATABASE = "oracle";

	/* Name Pools */
	public final static String[] MALE_NAME_POOL = {
		"Anthony", "Bob", "Carl", "David", "Earl", "Frank", "George", "Henry",
		"Ian", "John", "Kory", "Luis", "Matthew", "Nick", "Ox", "Paul", "Quincey",
		"Richard", "Steven", "Troy", "Ulfred", "Vincent", "William"
	};

	public final static Integer MALE_POOL_SIZE = MALE_NAME_POOL.length;
	
	public final static String[] FEMALE_NAME_POOL = {
		"Amanda", "Brittney", "Catherine", "Darcy", "Emily", "Francine", "Georgina", "Haley",
		"Ida", "Jamie", "Kelly", "Linda", "Megan", "Nina", "Olga", "Patricia", "Quinn",
		"Rachel", "Sarah", "Tara", "Usagi", "Valentia", "Wendy"
	};
	public final static Integer FEMALE_POOL_SIZE = FEMALE_NAME_POOL.length;
	
	public final static String[] FAMILY_NAME_POOL = {
		"Anderson", "Brown", "Clark", "Donohue", "Evans", "Ford", "Green", "Hill", "I",
		"Jones", "King", "Lee", "Matthews", "Nelson", "Olson", "Peterson", "Quintero", "Robinson",
		"Stevens", "Torres", "Williams"
	};
	public final static Integer FAMILY_POOL_SIZE = FAMILY_NAME_POOL.length;
}
