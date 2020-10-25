public class Status {

    // 传教士数
    int M;

    // 野人数
    int C;

    // 船数
    int B;

    // 深度
    int depth;

    // 0表在open表，1表在closed表
    int oorc;

    // 下一个节点的数组下标
    int point;

    Status() {
        M = 0;
        C = 0;
        B = 0;
        depth = 0;
        oorc = 0;
        point = -1;
    }

    Status(int m, int c, int b, int depth, int oorc, int point) {
        M = m;
        C = c;
        B = b;
        this.depth = depth;
        this.oorc = oorc;
        this.point = point;
    }

}
