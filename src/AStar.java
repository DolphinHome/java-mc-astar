public class AStar {

    // 系统容忍最大数值
    final int N = 65535;

    // 船上可容纳人数
    private int kofmc;

    // 野人和传教士人数
    public int nofmc;

    // 当前已经产生的结点序号
    private int number;

    // 存放状态节点
    private Status tree[] = new Status[N];

    // 存放路径
    public Display dp[] = new Display[N];

    // 找到最优解的标识
    public int found = 0;

    public int Start = 0;

    // 路径节点数
    public int num;

    private boolean large = false;

    public AStar(int nofmc, int kofmc) {
        this.nofmc = nofmc;
        this.kofmc = kofmc;
        if(this.nofmc >= N) {
            large = true;
            found = -1;
            System.out.println("输入太大！");
            return;
        }
        for(int i = 0; i < N; i++) {
            tree[i] = new Status();
            dp[i] = new Display();
        }
    }

    // 估计函数
    private float estimate(int M,int C,int B,int depth) {
        float retu = depth+B+((float)M+C)/kofmc;
        return retu;
    }

    //总的约束条件
    private boolean res(int M,int C) {
        return ((M==C)&&(M<=nofmc)&&(C<=nofmc)&&(M>=0)&&(C>=0))||((M==0)&&(C<=nofmc)&&(C>=0))||((M==nofmc)&&(C>=0)&&(C<=nofmc));
    }

    //判断是否为父结点
    private boolean isParent(int M, int C, int B, int depth, int target) {
        Status ps = null;
        ps=new Status();
        int point = target;
        ps.M = tree[point].M;
        ps.C = tree[point].C;
        ps.B = tree[point].B;
        ps.depth = tree[point].depth;
        ps.point = tree[point].point;
        while(ps.point != -1) {
            int ms,cs,bs;
            ms = ps.M;
            cs = ps.C;
            bs = ps.B;
            if((M==ms)&&(C==cs)&&(B!=bs)) {
                return true;
            }
            point = ps.point;
            ps.M = tree[point].M;
            ps.C = tree[point].C;
            ps.B = tree[point].B;
            ps.depth = tree[point].depth;
            ps.point = tree[point].point;
        }
        return false;
    }

    // 生成新节点
    private void create(int M,int C,int B,int depth,int target) {
        if(res(M,C)) {
            if(!(isParent(M,C,B,depth,target))) {
                // 在不在表中的标志
                int signal=0;
                for(int i = 0; i <= number; i++) {
                    int sign = tree[i].oorc;
                    int ms = tree[i].M;
                    int cs = tree[i].C;
                    int bs = tree[i].B;
                    int ds = tree[i].depth;
                    // 如果在open表中
                    if(sign == 0) {
                        if((M==ms)&&(C==cs)&&(B!=bs)) {
                            signal=1;
                            if(depth<(ds-1)) {
                                tree[i].point = target;
                                tree[i].depth = tree[target].depth+1;
                            }
                            break;
                        }
                    }
                    // 如果在closed表中
                    if(sign==1) {
                        if((M==ms)&&(C==cs)&&(B!=bs)) {
                            signal=1;
                            if(depth<(ds-1)) {
                                tree[i].point = target;
                                tree[i].depth = tree[target].depth+1;
                            }
                        }
                    }
                }
                //如果不在表中
                if(signal == 0) {
                    number++;
                    if(number >= N) {
                        found = -1;
                        System.out.println("数值太大！");
                        return;
                    }
                    tree[number].M=M;
                    tree[number].C=C;
                    tree[number].B=(B==0?1:0);
                    tree[number].depth=tree[target].depth+1;
                    tree[number].point = target;
                    tree[number].oorc = 0;
                }
            }
        }
    }

    //扩展节点
    private void extend(int M,int C,int B,int depth,int target) {
        if(B == 1) {
            for(int i = 1; i <= kofmc; i++) {
                M -= i;
                create(M,C,B,depth,target);
                M += i;
            }
            for(int j = 1; j <= kofmc; j++) {
                C -= j;
                create(M,C,B,depth,target);
                C += j;
            }
            for(int k = 1; k < kofmc; k++) {
                M -= k;
                for(int x = 1; x <= ((kofmc>2*k)?k:(kofmc-k)); x++) {
                    C -= x;
                    create(M,C,B,depth,target);
                    C += x;
                }
                M+=k;
            }
        } else {
            for(int i = 1; i <= kofmc; i++) {
                M += i;
                create(M,C,B,depth,target);
                M -= i;
            }
            for(int j = 1; j <= kofmc; j++) {
                C += j;
                create(M,C,B,depth,target);
                C -= j;
            }
            for(int k = 1; k < kofmc; k++) {
                M += k;
                for(int x=1; x<=((kofmc>2*k)?k:(kofmc-k)); x++) {
                    C += x;
                    create(M,C,B,depth,target);
                    C -= x;
                }
                M -= k;
            }

        }
    }

    private void change(int target) {
        tree[target].oorc=1;
    }

    private int excellent() {
        float min=32767;
        int target=-1;
        for(int i = 0; i <= number; i++) {
            int or=tree[i].oorc;
            if(or == 0) {
                int M, C, B, depth;
                float m;
                M = tree[i].M;
                C = tree[i].C;
                B = tree[i].B;
                depth = tree[i].depth;
                m = estimate(M, C, B, depth);
                if(m < min) {
                    min = m;
                    target = i;
                }
            }
        }
        return target;
    }

    public void start() {
        if(large) {
            found = -1;
            return;
        }
        Start=1;
        found=0;
        number=0;
        // 初始化tree
        tree[0].M=nofmc;
        tree[0].C=nofmc;
        tree[0].B=1;
        tree[0].depth=0;
        tree[0].oorc=0;
        tree[0].point = -1;
        int target=0;
        int point;
        point = target;
        while(point != -1) {
            int M,C,B,depth,mt,ct;
            M = tree[target].M;
            C = tree[target].C;
            B = tree[target].B;
            depth = tree[target].depth;
            extend(M,C,B,depth,target);
            change(target);
            target=excellent();
            if(target == -1) {
                System.out.println("no solution");
                break;
            }
            if(number >= N) {
                found = -1;
                System.out.println("too large number course exit!");
                break;
            }
            point = target;
            mt=tree[target].M;
            ct=tree[target].C;
            if((mt==0)&&(ct==0)) {
                int ps;
                ps = target;
                int numtemp=0;
                while(ps != -1) {
                    numtemp++;
                    ps = tree[ps].point;
                }
                num = numtemp;
                ps = target;
                while(ps != -1) {
                    numtemp--;
                    dp[numtemp].missi =tree[ps].M;
                    dp[numtemp].canni =tree[ps].C;
                    dp[numtemp].boat=tree[ps].B;
                    ps = tree[ps].point;
                }
                found=1;
                break;
            }
        }
    }

}
