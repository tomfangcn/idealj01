package bootstrap.test;


import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import static tool.FlinkEnvTools.*;

public class FirstJob {


    public static void main(String[] args) throws Exception {


        final StreamExecutionEnvironment env = getEnv();

        DataStream<Person> input1 = env.fromElements(
                new Person("ZHANSAN", 10),
                new Person("lisi", 9),
                new Person("wangwu", 1)
        );

        DataStreamSource<Person> input2 = env.fromCollection(MockInput.persons());

        DataStream<String> socket1 = env.socketTextStream("localhost", 9999);


//        DataStream<String> file1 = args.length >= 1 ? env.readTextFile(args[0])
//                : env.readTextFile("file:///path");


        DataStream<Person> filter = input2.filter(p -> p.getAge() >= 9);

        filter.print();

        socket1.map(stat -> stat.concat("!!")).print();

        env.execute("first job");

    }

}
