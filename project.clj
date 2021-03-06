(defproject vote-simple "0.1.0-SNAPSHOT"

  :description "FIXME: write description"
  :url "http://example.com/FIXME"

  :dependencies [[org.clojure/clojure "1.7.0"]
                 [selmer "0.9.5"]
                 [markdown-clj "0.9.82"]
                 [environ "1.0.1"]
                 [metosin/ring-middleware-format "0.6.0"]
                 [metosin/ring-http-response "0.6.5"]
                 [bouncer "0.3.3"]
                 [org.clojure/tools.nrepl "0.2.12"]
                 [org.webjars/bootstrap "3.3.5"]
                 [org.webjars/jquery "2.1.4"]
                 [com.taoensso/tower "3.0.2"]
                 [com.taoensso/timbre "4.1.4"]
                 [com.fzakaria/slf4j-timbre "0.2.1"]
                 [compojure "1.4.0"]
                 [ring-webjars "0.1.1"]
                 [ring/ring-defaults "0.1.5"]
                 [ring-ttl-session "0.3.0"]
                 [ring "1.4.0" :exclusions [ring/ring-jetty-adapter]]
                 [mount "0.1.4" :exclusions [ch.qos.logback/logback-classic]]
                 [migratus "0.8.7"]
                 [conman "0.2.7"]
                 [com.h2database/h2 "1.4.188"]
                 [http-kit "2.1.19"]
                 [hiccup "1.0.5"]
                 [com.taoensso/sente "1.6.0"]]

  :min-lein-version "2.0.0"
  :uberjar-name "vote-simple.jar"
  :jvm-opts ["-server"]

  :main vote-simple.core
  :migratus {:store :database}

  :plugins [[lein-environ "1.0.1"]
            [migratus-lein "0.2.0"]
            [lein-cljsbuild "1.1.1"]]
  :resource-paths ["resources" "target/cljsbuild"]
  :cljsbuild
  {:builds {:app {:source-paths ["src-cljs"]
                  :compiler {:output-to     "resources/public/js/app.js"
                             :output-dir    "resources/public/js/out"
                             :source-map    "resources/public/js/out.js.map"
                             :externs       ["react/externs/react.js"]
                             :optimizations :whitespace
                             :asset-path "/js/out"
                             :pretty-print  true}}}}
  :profiles
  {:uberjar {:omit-source true
             :env {:production true}
             :hooks ['leiningen.cljsbuild]
             :aot :all
             :source-paths ["env/prod/clj"]}
             :cljsbuild {:jar true
               :builds {:app
                        {:compiler
                         {:optimizations :advanced
                          :pretty-print false}}}}
   :dev           [:project/dev :profiles/dev]
   :test          [:project/test :profiles/test]
   :project/dev  {:dependencies [[prone "0.8.2"]
                                 [ring/ring-mock "0.3.0"]
                                 [ring/ring-devel "1.4.0"]
                                 [pjstadig/humane-test-output "0.7.0"]
                                 [mvxcvi/puget "1.0.0"]
                                 [org.clojure/clojurescript "1.7.170"]
                                 [buddy "0.8.2"]]
                  
                  
                  :source-paths ["env/dev/clj"]
                  :repl-options {:init-ns vote-simple.core}
                  :injections [(require 'pjstadig.humane-test-output)
                               (pjstadig.humane-test-output/activate!)]
                  ;;when :nrepl-port is set the application starts the nREPL server on load
                  :env {:dev        true
                        :port       3000
                        :nrepl-port 7000
                        :log-level  :trace}}
   :project/test {:env {:test       true
                        :port       3001
                        :nrepl-port 7001
                        :log-level  :trace}}
   :profiles/dev {}
   :profiles/test {}})
