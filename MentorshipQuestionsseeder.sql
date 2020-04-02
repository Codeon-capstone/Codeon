use codeon_db;

insert into posts (user_id, post_type_id, title, body, answer, date_time, employer) values (8, 1,  'What is fizzBuzz','can you solve fizzBuzz','In order to know if we should write “Fizz” or “Buzz”, we have to go through every number in that range. We can do that using a loop. The loop is a way that we can do something again and again, for a defined number of times or as long as we need to. If we’re driving down a street, a loop construct might be thought of as, while my car has gas, keep driving. Or if we know how long we need to drive for, we could say, keep driving for 10 miles. In this loop, we’ll start the first number, 0, then add 1 to get to the next, like so, all the way until we’ve reached 100:', '2019-06-17', 'Alumnus');
insert into posts (user_id, post_type_id, title, body, answer, date_time, employer) values (3, 1,  'Database question','In databases, what is the difference between a delete statement and a truncate statement?','The TRUNCATE command is like a DELETE command without the WHERE clause with much less of a safety net.', '2019-06-17', 'Alumnus');
insert into posts (user_id, post_type_id, title, body, answer, date_time, employer) values (20, 1,  'Agile environment advantages','What are the benefits of working in an Agile environment?','You can deploy software quicker, so your customer can get value sooner rather than later. You can better adapt to change and respond faster. You can get immediate feedback (which also improves team morale)', '2019-06-17', 'Alumnus');
insert into posts (user_id, post_type_id, title, body, answer, date_time, employer) values (5, 1,  'Agile environment disadvantage','What are the disadvantages of Agile Project Management','It''s more difficult to measure progress than it is in Waterfall because progress happens across several cycles. Agile demands more time and energy from everyone because developers and customers must constantly interact with each other. You need a long-term vision for the product and actively work on communicating it', '2019-06-17', 'Alumnus');
insert into posts (user_id, post_type_id, title, body, answer, date_time, employer) values (9, 1,  'found this interview question','Describe and code an algorithm that returns the first duplicate character in a string?','public class FirstDupCharacter { public static void main(String[] args) { System.out.println(findDupCharacter("abcdefghiaklmno")); } private static Character findDupCharacter(final String input) { final Set set = new HashSet(); Character dup = null; for (int i = 0; i &lt; input.length(); i++) { if (set.contains(input.charAt(i))) { dup = input.charAt(i); break; } else { set.add(input.charAt(i)); } } return dup; } }', '2019-06-17', 'Alumnus');
insert into posts (user_id, post_type_id, title, body, answer, date_time, employer) values (18, 1,  'Array problems','In a given sorted array of integers remove all the duplicates.','Pseudo code: Create an auxiliary array temp[] to store unique elements.Traverse input array and one by one copy unique elements of arr[] to temp[]. Also keep track of count of unique elements. Let this count be j.Copy j elements from temp[] to arr[] and return j', '2019-06-17', 'Alumnus');
insert into posts (user_id, post_type_id, title, body, answer, date_time, employer) values (1, 1,  'interview question','Determine the smallest area of a square that encapsulates all  given points in a list.','Pseudo code: Generates all possible squares with area (sqrt(k)) * (sqrt(k)) to the maximum bounding square of all points. The square''s bottom left point can be anywhere within the bounding square. Constraints like minimum size square required to accommodate at least k points(sqrt(k)) are considered. If a square encloses at least k points and area is lesser than current min area, then update area.', '2019-06-17', 'Alumnus');
insert into posts (user_id, post_type_id, title, body, answer, date_time, employer) values (12, 1,  'linked list','What is a linked list and when is it better to use a linked list than an array?','One advantage of the linked list is that elements can be added to it indefinitely, while an array will eventually get filled or have to be resized (a costly operation that isn''t always possible). Elements are also easily removed from a linked list whereas removing elements from an array leaves empty spaces that are a waste of computer memory', '2019-06-17', 'Alumnus');
insert into posts (user_id, post_type_id, title, body, answer, date_time, employer) values (4, 1,  'Question about methods','What is a final method?','The final modifier for finalizing the implementations of classes, methods, and variables.We can declare a method as final, once you declare a method final it cannot be overridden. So, you cannot modify a final method from a sub class.The main intention of making a method final would be that the content of the method should not be changed by any outsider.', '2019-06-17', 'Alumnus');
insert into posts (user_id, post_type_id, title, body, answer, date_time, employer) values (7, 1,  'Just a question i though of in case your applying at a job that uses MySQL','What is a join in MySQL?','Services', '2019-06-17', 'Alumnus');
insert into posts (user_id, post_type_id, title, body, answer, date_time, employer) values (11, 1,  'Just a question i though of in case your applying at a job that uses MySQL','What is a join in MySQL?','Services', '2019-06-17', 'Alumnus');
insert into posts (user_id, post_type_id, title, body, answer, date_time, employer) values (17, 1,  'Just a question i though of in case your applying at a job that uses MySQL','What is a join in MySQL?','Services', '2019-06-17', 'Alumnus');
insert into posts (user_id, post_type_id, title, body, answer, date_time, employer) values (12, 1,  'Just a question i though of in case your applying at a job that uses MySQL','What is a join in MySQL?','Services', '2019-06-17', 'Alumnus');
insert into posts (user_id, post_type_id, title, body, answer, date_time, employer) values (4, 1,  'Just a question i though of in case your applying at a job that uses MySQL','What is a join in MySQL?','Services', '2019-06-17', 'Alumnus');
insert into posts (user_id, post_type_id, title, body, answer, date_time, employer) values (9, 1,  'Just a question i though of in case your applying at a job that uses MySQL','What is a join in MySQL?','Services', '2019-06-17', 'Alumnus');