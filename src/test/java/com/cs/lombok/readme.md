#val
Finally! Hassle-free final local variables.

```
public class LombokVal {

	public static void main(String[] args) {
		example(); example2();
	}

	public static void example() {
		val example = new ArrayList<String>(); example.add("Hello, World!"); val foo = example.get(0);
		System.out.println(foo);
	}

	public static void example2() {
		val map = new HashMap<Integer, String>(); map.put(0, "zero"); map.put(5, "five");
		for (val entry : map.entrySet()) {
			System.out.printf("%d: %s\n", entry.getKey(), entry.getValue());
		}
	}


```

#@NonNull
or: How I learned to stop worrying and love the NullPointerException.

```
public class LombokNonNull {

	private String name;

	public LombokNonNull(@NonNull String name) {
		this.name = name;
	}

	public static void main(String[] args) {
		new LombokNonNull(null);
	}

}
```

#@Cleanup
Automatic resource management: Call your close() methods safely with no hassle.
#@Getter / @Setter
Never write public int getFoo() {return foo;} again.
#@ToString
No need to start a debugger to see your fields: Just let lombok generate a toString for you!
#@EqualsAndHashCode
Equality made easy: Generates hashCode and equals implementations from the fields of your object.
#@NoArgsConstructor, @RequiredArgsConstructor and @AllArgsConstructor
Constructors made to order: Generates constructors that take no arguments, one argument per final / non-null field, or one argument for every field.
#@Data
All together now: A shortcut for #@ToString, #@EqualsAndHashCode, #@Getter on all fields, and #@Setter on all non-final fields, and #@RequiredArgsConstructor!
#@Value
Immutable classes made very easy.
#@Builder
... and Bob's your uncle: No-hassle fancy-pants APIs for object creation!
#@SneakyThrows
To boldly throw checked exceptions where no one has thrown them before!
#@Synchronized
synchronized done right: Don't expose your locks.
#@Getter(lazy=true)
Laziness is a virtue!
#@Log
Captain's Log, stardate 24435.7: "What was that line again?"
#Configuration system
Lombok, made to order: Configure lombok features in one place for your entire project or even your workspace.
#Experimental features
Here be dragons: Extra features which aren't quite ready for prime time yet.


#[官网](https://projectlombok.org/features/index.html)